package Project1.Beans;

import java.math.*;
import java.util.*;
import Project1.Beans.Approval.*;

public class Reimbursement {
	private BigInteger id;
	private BigDecimal awarded;
	private boolean isCancelled;
	private boolean isPending;
	private Event event;
	private String justification;
	private String reasonExceededMax;
	private HashMap<String, Approval> approvals;
	
	/**
	 * @param awarded
	 * @param isCancelled
	 * @param isPending
	 * @param justification
	 * @param reasonExceededMax
	 */
	public Reimbursement(BigInteger id, String justification) {
		this.id = id;
		this.awarded = new BigDecimal(0);
		this.isCancelled = false;
		this.isPending = true;
		this.justification = justification;
		this.reasonExceededMax = null;
		event = null;
		approvals = new HashMap<>();
	}
	
	/**
	 * @param awarded
	 * @param isCancelled
	 * @param isPending
	 * @param justification
	 * @param reasonExceededMax
	 */
	public Reimbursement(BigInteger id, BigDecimal awarded,
						 boolean isCancelled, boolean isPending, 
						 String justification, String reasonExceededMax) {
		this.id = id;
		this.awarded = awarded;
		this.isCancelled = isCancelled;
		this.isPending = isPending;
		this.justification = justification;
		this.reasonExceededMax = reasonExceededMax;
		event = null;
		approvals = new HashMap<>();
	}
	
	public BigInteger getId() {
		return id;
	}

	public BigDecimal getAwarded() {
		return awarded;
	}

	public void setAwarded(BigDecimal awarded) {
		this.awarded = awarded;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getReasonExceededMax() {
		return reasonExceededMax;
	}

	public void setReasonExceededMax(String reasonExceededMax) {
		this.reasonExceededMax = reasonExceededMax;
	}
	
	public Approval retrieveApproval(String type) {
		return approvals.get(type);
	}

	public Approval insertApproval(String type, Approval approval) {
		return approvals.put(type, approval);
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", awarded=" + awarded + ", isCancelled=" + isCancelled + ", isPending="
				+ isPending + ", event=" + event + ", justification=" + justification + ", reasonExceededMax="
				+ reasonExceededMax + ", approvals=" + approvals + "]";
	}
}