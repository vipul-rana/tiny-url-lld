package com.systemdesign.tinyurl.repository;

import com.systemdesign.tinyurl.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TinyUrlRepository extends JpaRepository<UrlMapping, String> {

    Optional<UrlMapping> getByShortUrl(String shortUrl);

}
