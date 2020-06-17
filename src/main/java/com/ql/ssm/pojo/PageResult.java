package com.ql.ssm.pojo;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//结果集总数
    private Long total;
    //当前页的结果集
    private List<?> rows;
 
    public PageResult(Long total, List <?> rows) {
        this.total = total;
        this.rows = rows;
    }

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageResult [total=" + total + ", rows=" + rows + "]";
	}
    
    
}
