package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer> {

    @Query("SELECT m FROM Machine m " +
            "WHERE (m.validTo IS NULL " +
            "OR m.validTo > CURRENT_TIMESTAMP) " +
            "ORDER BY m.name ASC")
    List<Machine> findAllValidMachines();

    @Query("SELECT m FROM Machine m " +
            "WHERE (m.validTo IS NULL " +
            "OR m.validTo > CURRENT_TIMESTAMP) AND " +
            "m.machineCatalog.id = :id " +
            "ORDER BY m.id ASC")
    List<Machine> findAllMachinesByType(Integer id);
}
