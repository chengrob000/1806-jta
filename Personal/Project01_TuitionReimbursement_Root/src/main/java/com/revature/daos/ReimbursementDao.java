package com.revature.daos;

import java.util.List;

import com.revature.beans.Reimbursement;

public interface ReimbursementDao {
	public Reimbursement selectReimbursementById(Integer id);
	public List<Reimbursement> selectAllReimbursementByEmpId(Integer id);
	public List<Reimbursement> selectAllReimbursementByApproverId(Integer id);
	public Integer deleteReimbursementById(Integer id);
	public Boolean insertReimbursementViaSp(Reimbursement reim);
	public Boolean updateReimbursementViaSp(Reimbursement reim);
	public Reimbursement selectReimbursementByName(String name);
}
