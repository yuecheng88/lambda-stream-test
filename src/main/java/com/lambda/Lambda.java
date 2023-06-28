package com.lambda;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * https://blog.csdn.net/weixin_43296313/article/details/122605094
 */
public class Lambda {
    public static void main(final String[] args) {
       final List<TT> list = new ArrayList<>();
       list.add(new TT(1L,"xiao"));
       list.add(new TT(2L,"hua"));

        //
        final List<Long> ids = list.stream().map(TT::getId).toList();
        final Map<Long,TT> ttMap = list.stream().collect(Collectors.toMap(TT::getId, Function.identity()));

        //filter
        final List<TT> ids2 = list.stream().filter(StringUtils::isEmpty).toList();
        final List<TT> ids3 = list.stream().filter(t->t.id>1).toList();

        //去重
        final List<Long> ids11 = ids.stream().distinct().toList();
        final List<TT> ids12 = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TT::getId))), ArrayList::new));
        final List<TT> ids13 = list.stream().filter(distinctByKey(s->s.getId())).toList();

    }


    //定义一个Predicate函数
    public static <T> Predicate<T> distinctByKey(final Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


}
class TT{
    Long id;
    String name;

    public TT(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}