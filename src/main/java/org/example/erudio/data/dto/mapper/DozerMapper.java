package org.example.erudio.data.dto.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.MapperAware;
import com.github.dozermapper.core.MapperModelContext;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {

   private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

   public static <O, D> D parseObject(O origin, Class<D> destination) {
       return mapper.map(origin, destination);
   }

   public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
       List<D> destList = new ArrayList<D>();
       for (O o : origin) {
           destList.add(parseObject(o, destination));
       }
       return  destList;
   }
}
