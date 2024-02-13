package com.kingsforge.kingsforge.business.services.impl;

import java.util.List;

import com.kingsforge.kingsforge.business.entity.Supplier;
import com.kingsforge.kingsforge.business.services.SupplierService;
import com.kingsforge.kingsforge.persistance.SupplierRepository;
import com.kingsforge.kingsforge.persistance.impl.SupplierRepositoryImpl;

public class SupplierServiceImpl implements SupplierService {
    private SupplierRepository supplierRepository = new SupplierRepositoryImpl();

    public List<Supplier> listSuppliers() throws RuntimeException{
        return supplierRepository.listSuppliers();
    }
    public Supplier getSupplierById(int supplier_id, String language) throws RuntimeException{
        return supplierRepository.getSupplierById(supplier_id, language);
    }
}
