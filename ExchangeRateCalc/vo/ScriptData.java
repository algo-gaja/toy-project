package vo;

import java.util.List;

public class ScriptData {
	private Props props;
	
	public String getCountries() {
		return props.pageProps.dehydratedState.queries.get(0).state.data.result.toString();
	}
	
	private class Props {
		PageProps pageProps;
	}
	
	private class PageProps {
		DehydratedState dehydratedState;
	}
	
	private class DehydratedState {
		List<Query> queries;
	}
	
	private class Query {
		State state;
	}
	
	private class State {
		Data data;
	}
	
	private class Data {
		Object result;
//		List<Country> result;
	}
}
