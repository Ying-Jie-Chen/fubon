package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.model.dto.CarInsuranceTermDTO;
import com.fubon.ecplatformapi.model.dto.PaymentRecordDTO;
import com.fubon.ecplatformapi.model.dto.UnpaidRecordDTO;
import com.fubon.ecplatformapi.model.entity.CarInsuranceTerm;
import com.fubon.ecplatformapi.model.entity.NFNV02Entity;
import com.fubon.ecplatformapi.model.entity.NFNV03Entity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;


public class InsuranceEntityMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static CarInsuranceTermDTO mapToCarInsuranceTermDTO(CarInsuranceTerm entity) {
        return modelMapper.map(entity, CarInsuranceTermDTO.class);
    }
    public static UnpaidRecordDTO mapToUnpaidRecordDTO(NFNV02Entity entity) {
        modelMapper.addMappings(new PropertyMap<NFNV02Entity, UnpaidRecordDTO>() {
            @Override
            protected void configure() {
                map().setPolyNo(source.getPolyno());
            }
        });
        return modelMapper.map(entity, UnpaidRecordDTO.class);
    }

    public static PaymentRecordDTO mapToPaymentRecordDTO(NFNV03Entity entity) {
            modelMapper.addMappings(new PropertyMap<NFNV03Entity, PaymentRecordDTO>() {
                @Override
                protected void configure() {
                    map().setPolyNo(source.getPolyno());
                }
            });
            return modelMapper.map(entity, PaymentRecordDTO.class);
    }
}
