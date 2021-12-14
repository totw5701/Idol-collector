package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.bundle.BundleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BundleService {

    private final BundleRepository bundleRepository;

    @Transactional
    void save() {

    }

}
