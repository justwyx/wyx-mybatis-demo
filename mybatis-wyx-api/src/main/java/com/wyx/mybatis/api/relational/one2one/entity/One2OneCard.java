package com.wyx.mybatis.api.relational.one2one.entity;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public class One2OneCard {
	private Integer id;

	private String idCard;

	private String name;

	private Byte sex;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "One2OneCard{" +
				"id=" + id +
				", idCard='" + idCard + '\'' +
				", name='" + name + '\'' +
				", sex=" + sex +
				'}';
	}
}
