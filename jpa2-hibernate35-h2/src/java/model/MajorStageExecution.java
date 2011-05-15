package model;

import java.io.Serializable;
import java.util.Calendar;

import util.ObjectUtil;

/**
 * This class represents the execution of a stage.
 * @author Ariss Zhao
 */
public class MajorStageExecution implements Serializable {

	private static final long serialVersionUID = 1L;

	// keys
	private long id;

	// properties
	private Calendar startTime;
	private Calendar endTime;
	
	public MajorStageExecution() {
		this(0);
	}

	public MajorStageExecution(long id) {
		this(new Builder().id(id));
	}
	
	public MajorStageExecution( MajorStageExecution execution ) {
		this.id = execution.id;
		this.startTime = ObjectUtil.clone( execution.startTime );
		this.endTime = ObjectUtil.clone( execution.endTime );
	}

	/**
	 * Returns whether this execution has commenced.
	 * @return
	 */
	public boolean started() {
		return startTime != null;
	}

	/**
	 * Returns whether this execution has finished.
	 * @return
	 */
	public boolean ended() {
		return endTime != null;
	}

	/**
	 * Returns the id of this execution
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * This method is not intended to be used internally by the system for
	 * thread safety.  It is included to facilitate the marshaling of this
	 * object between the client and the server.
	 * @param id the id of this execution
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the start time of this execution.  If it is null, it means 
	 * the execution hasn't commenced. 
	 * @return the start time of this execution
	 */
	public Calendar getStartTime() {
		return ObjectUtil.clone( startTime );
	}

	
	/**
	 * This method is not intended to be used internally by the system for
	 * thread safety.  It is included to facilitate the marshaling of this
	 * object between the client and the server.
	 * @param startTime the start time of this execution
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = ObjectUtil.clone( startTime );
	}

	/**
	 * Returns the end time of this execution.  If it is null, it means 
	 * the execution hasn't ended.
	 * @return the end time of this execution
	 */
	public Calendar getEndTime() {
		return ObjectUtil.clone( endTime );
	}	

	/**
	 * This method is not intended to be used internally by the system for
	 * thread safety.  It is included to facilitate the marshaling of this
	 * object between the client and the server. 
	 * @param endTime the end time of this execution
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = ObjectUtil.clone( endTime );
	}
	

	@Override
	public boolean equals(Object o) {
		// first determine if they are the same object reference
		if (this == o) {
			return true;
		}

		// make sure they are the same class
		if (o == null || o.getClass() != getClass()) {
			return false;
		}

		MajorStageExecution execution = (MajorStageExecution) o;

		if (this.id == execution.id) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}

	/**
	 * instance builder
	 */
	public static class Builder {
		private long id;
		private Calendar startTime;
		private Calendar endTime;
		
		public Builder id(long id) {
			this.id = id;
			return this;
		}

		public Builder startTime(Calendar startTime) {
			this.startTime = ObjectUtil.clone( startTime );
			return this;
		}
		
		public Builder endTime(Calendar endTime) {
			this.endTime = ObjectUtil.clone( endTime );
			return this;
		}
				
		public MajorStageExecution build() {
			return new MajorStageExecution(this);
		}
	}

	private MajorStageExecution(Builder builder) {
		this.id = builder.id;
		this.startTime = builder.startTime;
		this.endTime = builder.endTime;
	}
}
