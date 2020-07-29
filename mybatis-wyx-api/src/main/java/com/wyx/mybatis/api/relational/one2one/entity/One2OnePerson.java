package com.wyx.mybatis.api.relational.one2one.entity;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public class One2OnePerson {
	private Integer id;

	private String idCard;

	private String nickName;

	private One2OneCard card;

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public One2OneCard getCard() {
		return card;
	}

	public void setCard(One2OneCard card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return "One2OnePerson{" +
				"id=" + id +
				", idCard='" + idCard + '\'' +
				", nickName='" + nickName + '\'' +
				", card=" + card +
				'}';
	}
}
