package com.dael.ApiRest.persistence.repository;

import com.dael.ApiRest.persistence.entities.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {
}
