package com.videos.course.server.utils;


import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CopyUtils {

	/**
	 * 便于后续的封装
	 */
	public static <T> T copy(Object source, Class<T> clazz) {
			if (source == null) {
				return null;
			}
		T obj = null;
			try {
				obj = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		BeanUtils.copyProperties(source, obj);
		return obj;
	}

	/**
	 * 拷贝并封装成一个集合
	 * @param source
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> copy(List list, Class<T> clazz) {
		List<T> collect=null;
		/*	if (!CollectionUtils.isEmpty(source)){
            if (!CollectionUtils.isEmpty(source)){
                for (Object c: source) {
                    T obj = copy(c, clazz);
                    target.add(obj);
                }
            }
        }*/
		    collect = (List<T>) Optional.ofNullable(list).orElseGet(() -> {
				return new ArrayList();
			}).stream().map(l -> {
				T copy = copy(l, clazz);
				return copy;
			}).collect(Collectors.toList());
		return collect;
	}
}