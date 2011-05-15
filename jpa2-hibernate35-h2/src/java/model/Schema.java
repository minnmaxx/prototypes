package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schema implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int SEQUENCE_NOT_SET = Integer.MIN_VALUE;

	// keys (composite keys)
	private int sequence;
	private MajorStageExecution stageExecution;

	// properties
	private List<ChannelDefinition> channelDefinitions;

	public Schema() {
		//this(new MajorStageExecution(), 0);
		this(new MajorStageExecution(), SEQUENCE_NOT_SET );
	}

	public Schema(MajorStageExecution execution, int sequence) {
		this( new Builder().stageExecution(execution).sequence(sequence) );
	}
	
	public Schema(Schema schema) {
		this.sequence = schema.sequence;
		this.stageExecution = schema.stageExecution;
		this.channelDefinitions = 
			new ArrayList<ChannelDefinition>( schema.channelDefinitions );
	}

	/**
	 * Returns the sequence of this schema
	 * @return
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * Sets the sequence of this schema
	 * @param id
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * Return the stage execution instance to which this schema belongs.
	 * @return
	 */
	public MajorStageExecution getStageExecution() {
		return stageExecution;
	}

	/**
	 * Sets the stage execution instance to which this schema belongs.
	 * @param stageExecution
	 */
	public void setStageExecution(MajorStageExecution stageExecution) {
		this.stageExecution = stageExecution;
	}

	/**
	 * Return the channel definitions of this schema
	 * @return
	 */
	public List<ChannelDefinition> getChannelDefinitions() {
		return channelDefinitions;
	}

	/**
	 * Sets the channel definitions of this schema
	 * @param channelDefinitions
	 */
	public void setChannelDefinitions(List<ChannelDefinition> channelDefinitions) {
		if (channelDefinitions == null) {
			throw new IllegalArgumentException(
					"The list of channel definitions is null.");
		}
		this.channelDefinitions = channelDefinitions;
	}

	/**
	 * Adds a channel to this schema.
	 * @param channelDefinition
	 * @return
	 */
	public boolean addChannelDefinition(ChannelDefinition channelDefinition) {
		return channelDefinitions.add(channelDefinition);
	}
	
	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();

		buffer.append( "Schema(" );
		if(stageExecution != null) {
			buffer.append(stageExecution.getId());
			buffer.append(",");
		}
		buffer.append(sequence);
		buffer.append(",");
		
		for( int i = 0; i < channelDefinitions.size(); i++ ) {
			ChannelDefinition def = channelDefinitions.get(i);
			buffer.append(def.getDescription());
			buffer.append("[");
			buffer.append(def.getId());
			buffer.append(",");
			buffer.append(def.getColor());
			buffer.append("]");
			if( i < channelDefinitions.size()-1 ) {
				buffer.append( "," );
			}
		} 
		buffer.append(")");	
		
		return buffer.toString();
	}	

	/**
	 * Instance builder
	 */

	public static class Builder {
		private int sequence = SEQUENCE_NOT_SET;
		private MajorStageExecution stageExecution;
		private List<ChannelDefinition> channelDefinitions = new ArrayList<ChannelDefinition>();

		public Builder sequence(int sequence) {
			this.sequence = sequence;
			return this;
		}

		public Builder stageExecution(MajorStageExecution stageExecution) {
			this.stageExecution = stageExecution;
			return this;
		}

		public Builder channelDefinitions(
				List<ChannelDefinition> channelDefinitions) {
			this.channelDefinitions = channelDefinitions;
			return this;
		}

		public Schema build() {
			return new Schema(this);
		}
	}

	private Schema(Builder builder) {
		this.sequence = builder.sequence;
		this.stageExecution = builder.stageExecution;
		this.channelDefinitions = builder.channelDefinitions;
	}
}
