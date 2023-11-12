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
    private static final ModelMapper modelMapper = createModelMapper();

    private static ModelMapper createModelMapper() {
        ModelMapper mapper = new ModelMapper();
        configureUnpaidRecordDTO(mapper);
        configurePaymentRecordDTO(mapper);
        return mapper;
    }

    private static void configureUnpaidRecordDTO(ModelMapper mapper) {
        mapper.addMappings(new PropertyMap<NFNV02Entity, UnpaidRecordDTO>() {
            @Override
            protected void configure() {
                map().setPolyNo(source.getPolyno());
            }
        });
    }

    private static void configurePaymentRecordDTO(ModelMapper mapper) {
        mapper.addMappings(new PropertyMap<NFNV03Entity, PaymentRecordDTO>() {
            @Override
            protected void configure() {
                map().setPolyNo(source.getPolyno());
            }
        });
    }

    public static CarInsuranceTermDTO mapToCarInsuranceTermDTO(CarInsuranceTerm entity) {
        return modelMapper.map(entity, CarInsuranceTermDTO.class);
    }

    public static UnpaidRecordDTO mapToUnpaidRecordDTO(NFNV02Entity entity) {

        return modelMapper.map(entity, UnpaidRecordDTO.class);
    }

    public static PaymentRecordDTO mapToPaymentRecordDTO(NFNV03Entity entity){
        return modelMapper.map(entity, PaymentRecordDTO.class);
    }
}
