package com.wyx.springbootmybatis.pagehelper;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/30
 * @Date : 2020/7/30
 */
public class PageBean<T> extends PageInfo<T> {

	//当前页
	private int pageNo;

	public PageBean(List<T> list) {
		super(list, DEFAULT_NAVIGATE_PAGES);
		pageNo = super.getPageNum();
	}


	public int getPageNo() {
		return pageNo;
	}


	@Override
	public String toString() {
		return "PageBean{" +
				"pageNo=" + pageNo +
				", pageInfo=" + super.toString() +
				'}';
	}
}
