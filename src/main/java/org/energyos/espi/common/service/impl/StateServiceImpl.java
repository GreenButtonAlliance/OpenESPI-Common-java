package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.service.StateService;

import java.util.UUID;

public class StateServiceImpl implements StateService {

    @Override
    public String newState() {
        return UUID.randomUUID().toString();
    }
}
