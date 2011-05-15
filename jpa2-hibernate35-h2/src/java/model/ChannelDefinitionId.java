package model;

import java.io.Serializable;

public class ChannelDefinitionId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long majorStageExecutionId;

	public ChannelDefinitionId() {			
	}
	
	public ChannelDefinitionId( long id, long majorStageExecutionId ) {
		this.id = id;
		this.majorStageExecutionId = majorStageExecutionId;
	}
	
	public ChannelDefinitionId( ChannelDefinitionId id ) {
		this.id = id.id;
		this.majorStageExecutionId = id.majorStageExecutionId;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o != null && o instanceof ChannelDefinitionId) {
	    	ChannelDefinitionId that = (ChannelDefinitionId)o;
	        return this.id.equals(that.id) &&
	               this.majorStageExecutionId.equals(that.majorStageExecutionId);
	    } else {
	        return false;
	    }
	}
	
	@Override
	public int hashCode() {
		int result = (int) (this.id * 23 + this.majorStageExecutionId);
	    return result;
	}
	
	@Override
	public String toString() {
		return "ChannelDefinitionId(" + majorStageExecutionId + "," + id + ")";
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMajorStageExecutionId() {
		return majorStageExecutionId;
	}
	public void setMajorStageExecutionId(Long majorStageExecutionId) {
		this.majorStageExecutionId = majorStageExecutionId;
	}
}
