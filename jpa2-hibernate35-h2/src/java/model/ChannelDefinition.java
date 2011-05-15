package model;

import java.io.Serializable;

public class ChannelDefinition implements Serializable {

	private static final long serialVersionUID = 1L;

	// key
	//private MajorStageExecution majorStageExecution;
	//private long originalChannelId;
	
	private ChannelDefinitionId compositeId = new ChannelDefinitionId();

	// properties
	private String description;
	private int unit;
	private int color;

	public ChannelDefinition() {
		this(0);
	}

	public ChannelDefinition(long id) {
		this(new Builder().id(id));
	}

	public ChannelDefinition(ChannelDefinition def) {
		//this.originalChannelId = def.originalChannelId;
		//this.majorStageExecution = def.majorStageExecution;
		this.compositeId = new ChannelDefinitionId( def.compositeId );
		this.description = def.description;
		this.unit = def.unit;
		this.color = def.color;
	}
	
	public ChannelDefinitionId getCompositeId() {
		return compositeId;
	}

	public long getId() {
		return compositeId.getId();
	}

	public void setId(long id) {
		compositeId.setId(id);
	}

	//	public String getName() {
	//		return name;
	//	}
	//
	//	public void setName(String name) {
	//		this.name = name;
	//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//	public ChannelType getType() {
	//		return type;
	//	}
	//
	//	public void setType(ChannelType type) {
	//		this.type = type;
	//	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	//	public ChannelCategory getCategory() {
	//		return category;
	//	}
	//
	//	public void setCategory(ChannelCategory category) {
	//		this.category = category;
	//	}

//	public MajorStageExecution getMajorStageExecution() {
//		return majorStageExecution;
//	}

	public void setMajorStageExecution(MajorStageExecution majorStageExecution) {
		//ObjectUtil.checkInputNotNull(majorStageExecution);
		//this.majorStageExecution = majorStageExecution;
		this.compositeId.setMajorStageExecutionId(majorStageExecution.getId());
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

		ChannelDefinition channel = (ChannelDefinition) o;

		if ( this.compositeId.equals( channel.compositeId ) ) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return compositeId.hashCode();
	}

	/**
	 * instance builder
	 */
	public static class Builder {
		
		private MajorStageExecution majorStageExecution;
		private long id;
		private String description;
		private int unit;
		private int color;

		public Builder majorStageExecution(
				MajorStageExecution majorStageExecution) {
			this.majorStageExecution = majorStageExecution;
			return this;
		}

		public Builder id(long id) {
			this.id = id;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder unit(int unit) {
			this.unit = unit;
			return this;
		}

		public Builder color(int color) {
			this.color = color;
			return this;
		}

		public ChannelDefinition build() {
			return new ChannelDefinition(this);
		}
	}

	private ChannelDefinition(Builder builder) {
		//this.majorStageExecution = builder.majorStageExecution;
		this.compositeId.setId(builder.id);
		if( builder.majorStageExecution != null ) {
			this.compositeId.setMajorStageExecutionId(builder.majorStageExecution.getId());
		}
		this.description = builder.description;
		this.unit = builder.unit;
		this.color = builder.color;
	}
}
