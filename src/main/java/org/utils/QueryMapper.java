package org.utils;

import org.domain.Coordinates;
import org.enums.Lang;
import org.enums.Type;
import org.enums.Units;

import java.util.HashMap;
import java.util.Map;

public class QueryMapper {
    private Map<String, Object> queryMapper;

    public QueryMapper() {
        this.queryMapper = new HashMap<>();
    }

    public QueryMapper setLang(Lang lang){
        queryMapper.put("lang", lang.getLangCode());
        return this;
    }

    public QueryMapper setUnits(Units unit){
        queryMapper.put("units", unit.getUnitName());
        return this;
    }

    public QueryMapper setAccuracy(Type type){
        queryMapper.put("type", type.getValue());
        return this;
    }

    public QueryMapper setCityName(String cityName){
        queryMapper.put("q", cityName);
        return this;
    }

    public QueryMapper setCityId(long id){
        queryMapper.put("id", id);
        return this;
    }

    public QueryMapper setCoordinates(final Coordinates coordinates){
        queryMapper.put("lat", coordinates.getLat());
        queryMapper.put("lon", coordinates.getLon());
        return this;
    }

    public QueryMapper setCityZipCode(String zipCode){
        queryMapper.put("zip", zipCode);
        return this;
    }

    public Map<String, Object> build(){
        return queryMapper;
    }
}
