package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.repository.MachineRepository;
import org.crsoft.cartonplast.design.service.mapper.MachineMapper;
import org.crsoft.cartonplast.design.vo.res.MachineRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@MockBean(MachineService.class)
class MachineServiceTest {

    @Autowired
    private MachineService underTest;

    @MockBean
    private MachineRepository machineRepository;

    @MockBean
    private MachineMapper machineMapper;

    @Test
    void findAllValidMachines() {
        // Given
        List<MachineRes> expected = Arrays.asList(
                new MachineRes(1, "Machine 1", true),
                new MachineRes(2, "Machine 2", true),
                new MachineRes(3, "Machine 3", true)
        );

        // When
        when(underTest.findAllValidMachines()).thenReturn(expected);

        // Then
        assertEquals(expected, underTest.findAllValidMachines());
    }
}