package in.ar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.laxmi.entity.PlanEntity;


public interface PlanEntityRepo extends JpaRepository<PlanEntity, Integer> {

}
