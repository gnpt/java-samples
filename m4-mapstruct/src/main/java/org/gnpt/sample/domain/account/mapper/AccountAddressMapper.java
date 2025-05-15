package org.gnpt.sample.domain.account.mapper;

import org.gnpt.sample.domain.account.model.AccountAddress;
import org.mapstruct.Mapper;

@Mapper
public interface AccountAddressMapper {

    default String toFullAddress(AccountAddress address){
        return address == null
                ? null
                : address.getHouse() + " " + address.getStreet() + ", " + address.getCity() + ", " + address.getCountry();
    }

}
