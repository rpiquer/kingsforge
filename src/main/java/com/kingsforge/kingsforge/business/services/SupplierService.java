package com.kingsforge.kingsforge.business.services;

import java.util.List;

import com.kingsforge.kingsforge.business.entity.Supplier;

public interface SupplierService {
    public List<Supplier> listSuppliers() throws RuntimeException;
    public Supplier getSupplierById(int supplier_id, String language) throws RuntimeException;
}
