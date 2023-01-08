package ru.rinat.restservice.controller;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.rinat.restservice.entity.News;
import ru.rinat.restservice.dto.NewsDto;
import ru.rinat.restservice.service.NewsService;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class NewsController {
	
	final static Logger logger = LoggerFactory.getLogger(NewsController.class);

	private NewsService newsService;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	@GetMapping
	@Tag(name = "Список всех новостей", description = "Позволяет получить список всех новостей")
	@ApiResponses(value = { @ApiResponse(content = { @Content(mediaType = "application/json",
	 array = @ArraySchema(schema = @Schema(implementation = NewsDto.class)))})})
	public List<NewsDto> getNews() {
		logger.info("Get list news...");
		
		List<News> news = newsService.findAll();
		return news.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@PostMapping
	@Tag(name = "Создание новости", description = "Позволяет создать новую новость")
	@ApiResponses(value = { @ApiResponse(description = "Новость создана успешно", responseCode = "200",
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))), 
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public ResponseEntity<Object> create(@RequestBody News news) {
		logger.info(String.format("Create user with id %s", news.getId()));
		
		News savedNews = newsService.create(news);

		URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedNews.getId()).toUri();

		return ResponseEntity.created(locationUri).build();
	}

	@GetMapping("/{id}")
	@Tag(name = "Новость по индентификатору", description = "Возвращает новость по уникальному идентификатору")
	@ApiResponses(value = { @ApiResponse(description = "Новость найдена успешно", responseCode = "200",
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))),
	@ApiResponse(responseCode = "404", description = "Новость не найдена", content = @Content),
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public NewsDto getById(@PathVariable UUID id) {
		return convertToDto(newsService.findById(id));
	}

	@PutMapping("/{id}")
	@Tag(name = "Изменение новости", description = "Позволяет отредактировать поля новости")
	@ApiResponses(value = { @ApiResponse(description = "Новость изменёна успешно", responseCode = "200",
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))), 
	@ApiResponse(responseCode = "404", description = "Новость не найдена", content = @Content),
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public String update(@RequestBody News news, @PathVariable UUID id) {
		logger.info(String.format("Update news with id %s", news.getId()));

		checkIdNews(news, id);

		return newsService.update(news);
	}

	private void checkIdNews(News news, UUID id) {
		if (news.getId() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не верно задан id для параметра News");
		
		if (!news.getId().equals(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Параметр Id не соотвествует Id параметра News");
		}
	}
	
	@DeleteMapping("/{id}")
	@Tag(name = "Удаление новости", description = "Позволяет удалить новость")
	@ApiResponses(value = { @ApiResponse(description = "Новость удалёна успешно", responseCode = "200",
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))), 
	@ApiResponse(responseCode = "404", description = "Новость не найдена", content = @Content),
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public String delete(@RequestBody News news, @PathVariable UUID id) {
		logger.info(String.format("Delete news with id %s", news.getId()));

		checkIdNews(news, id);
		
		return newsService.delete(news);
	}

	@GetMapping("/user/{id}")
	@Tag(name = "Список новостей пользователя", description = "Позволяет получить список новостей пользователя")
	@ApiResponses(value = { @ApiResponse(content = { @Content(mediaType = "application/json",
			array = @ArraySchema(schema = @Schema(implementation = NewsDto.class)))})})
	public List<NewsDto> getNewsByUserCreator(@PathVariable UUID id) {
		logger.info("Get list news...");

		List<News> news = newsService.findByUserCreatorId(id);
		return news.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private NewsDto convertToDto(News news) {
		return modelMapper.map(news, NewsDto.class);
	}

}
