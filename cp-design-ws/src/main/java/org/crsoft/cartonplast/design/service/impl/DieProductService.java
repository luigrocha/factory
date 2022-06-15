package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.constant.CatalogStatusConstant;
import org.crsoft.cartonplast.common.constant.LogMessageConstant;
import org.crsoft.cartonplast.common.constant.MessagesConstant;
import org.crsoft.cartonplast.common.exception.ConflictException;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.common.model.CatalogStatus;
import org.crsoft.cartonplast.common.service.impl.CatalogStatusService;
import org.crsoft.cartonplast.design.model.DieProduct;
import org.crsoft.cartonplast.design.repository.DieProductRepository;
import org.crsoft.cartonplast.design.service.IDieProductService;
import org.crsoft.cartonplast.design.service.mapper.DieProductMapper;
import org.crsoft.cartonplast.design.vo.req.DieProductReq;
import org.crsoft.cartonplast.design.vo.res.DieProductRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author lpillaga on 12/06/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DieProductService implements IDieProductService {

    private final DieProductRepository dieProductRepository;
    private final DieProductMapper dieProductMapper;
    private final CatalogStatusService catalogStatusService;

    @Override
    public List<DieProductRes> findAllValidDieProducts() {
        return this.dieProductMapper.toDieProductResList(
                this.dieProductRepository.findAllValidDieProducts()
        );
    }

    @Override
    public List<DieProductRes> findAllAvailableDieProducts() {
        return this.dieProductMapper.toDieProductResList(
                this.dieProductRepository.findAllAvailableDieProducts()
        );
    }

    @Override
    @Transactional
    public DieProductRes save(DieProductReq dieProductReq) {
        Optional<CatalogStatus> catalogStatusOptional = catalogStatusService.findByTypeAndIsDefault(CatalogStatusConstant.DIE_PRODUCT_STATUS_CODE);

        if (catalogStatusOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + dieProductReq);
            throw new NotExistException("No existe un estado por defecto para prod troquelado");
        }

        boolean alreadyExist = this.dieProductRepository.existsByCodeAndIsNotDeleted(dieProductReq.getCode());
        if (alreadyExist) {
            log.error(LogMessageConstant.ERROR_DUPLICATE_RECORD_MESSAGE + dieProductReq);
            throw new ConflictException("Ya existe un producto troquelado con el código: " + dieProductReq.getCode());
        }

        DieProduct dieProduct = this.dieProductMapper.toDieProduct(dieProductReq);
        dieProduct.setStatus(catalogStatusOptional.get());
        dieProduct = this.dieProductRepository.save(dieProduct);
        return this.dieProductMapper.toDieProductRes(dieProduct);
    }

    @Override
    @Transactional
    public DieProductRes update(Integer id, DieProductReq dieProductReq) {
        Optional<DieProduct> optionalDieProduct = this.dieProductRepository.findById(id);

        if (optionalDieProduct.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + dieProductReq);
            throw new NotExistException(MessagesConstant.MESSAGE_NOT_FOUND);
        }

        CatalogStatus status = catalogStatusService
                .findById(dieProductReq.getStatusId())
                .orElseThrow(() -> {
                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + dieProductReq);
                    throw new NotExistException("No existe un estado con el id: " + dieProductReq.getStatusId());
                });

        DieProduct dieProduct = optionalDieProduct.get();
        dieProduct.setCode(dieProductReq.getCode());
        dieProduct.setName(dieProductReq.getName());
        dieProduct.setArea(dieProductReq.getArea());
        dieProduct.setLength(dieProductReq.getLength());
        dieProduct.setWidth(dieProductReq.getWidth());
        dieProduct.setGsmdis(dieProductReq.getGsmdis());
        dieProduct.setStatus(status);

        return this.dieProductMapper.toDieProductRes(dieProduct);
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        return dieProductRepository.findById(id)
                .map(dp -> {
                    dp.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
