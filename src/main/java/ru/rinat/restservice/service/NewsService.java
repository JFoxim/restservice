package ru.rinat.restservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rinat.restservice.entity.News;
import ru.rinat.restservice.exception.NewsNotFoundExeption;
import ru.rinat.restservice.repository.NewsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NewsService {

	final static Logger logger = LoggerFactory.getLogger(NewsService.class);

	private NewsRepository newsRepository;

	@Autowired
	public void setNewsRepository(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	public News create(News news) {
		return newsRepository.save(news);
	}

	public String update(News news) {
		checkExistNews(news);

		newsRepository.save(news);

		return String.format("Новость с id %s обновлёна", news.getId());
	}

	private void checkExistNews(News news) {
		if (!newsRepository.existsById(news.getId())) {
			throw new NewsNotFoundExeption("id - " + news.getId());
		}
	}

	public String delete(News news) {
		checkExistNews(news);
		newsRepository.delete(news);
		return String.format("Новость с id %s удалёна", news.getId());
	}


	public News findById(UUID id) {
		Optional<News> newsOptional = newsRepository.findById(id);

		if (newsOptional.isEmpty()) {
			throw new NewsNotFoundExeption("id - " + id);
		}

		return newsOptional.get();
	}

	public List<News> findAll() {
		return newsRepository.findAll();
	}

	public List<News> findByUserCreatorId(UUID userId) {
		return newsRepository.findByUserCreatorId(userId);
	}

	public boolean existsBySubject(String subject) {
		Optional<News> userOpt = newsRepository.findBySubject(subject);

		return userOpt.isPresent();
	}

}
