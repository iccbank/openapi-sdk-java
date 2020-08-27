package net.iccbank.openapi.sdk.enums;

public enum SearchTypeEnum {
	
	CURRENCY(1),
	
	CONTRACT_ADDRESS(2)
	
	;

	private int type;
	
	SearchTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	public static SearchTypeEnum valueOfByType(int type) {
		for (SearchTypeEnum e : values()) {
			if (e.getType() == type) {
				return e;
			}
		}
		return null;
	}
	
}
