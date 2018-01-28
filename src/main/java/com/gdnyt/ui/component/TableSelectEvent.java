package com.gdnyt.ui.component;

import java.util.List;

public interface TableSelectEvent {
	void select(List<String> tableNames);

	void dbClick(String tableName);
}
