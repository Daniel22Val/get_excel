package io.lacak.clone.live.zonelogic.Entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<ZoneEntity, Long> {

    List<ZoneEntity> findAllByHash(String hash);
}
