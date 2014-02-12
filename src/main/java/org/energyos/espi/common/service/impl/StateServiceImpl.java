package org.energyos.espi.common.service.impl;

import java.util.UUID;

import org.energyos.espi.common.service.StateService;

public class StateServiceImpl implements StateService {

    @Override
    public String newState() {
        return UUID.randomUUID().toString();
    }
}
