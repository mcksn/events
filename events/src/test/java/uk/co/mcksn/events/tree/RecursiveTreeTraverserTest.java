package uk.co.mcksn.events.tree;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@SuppressWarnings("rawtypes")
@RunWith(MockitoJUnitRunner.class)
public class RecursiveTreeTraverserTest {

	private RecursiveTreeTraverserImpl recursiveTreeTraverserImpl = new RecursiveTreeTraverserImpl();

	@Test
	public void WHEN_getRootTree_SHOULD_get_root_tree_GIVEN_3_level_tree_return_type_Treeable() {
		// Given
		Treeable mockTreeableArg = mock(Treeable.class, RETURNS_DEEP_STUBS);
		Treeable mockTreeableArgParent = mock(Treeable.class, RETURNS_DEEP_STUBS);
		Treeable expectedTreeableRoot = mock(Treeable.class, RETURNS_DEEP_STUBS);
		given(mockTreeableArg.getTreeModule().getParent()).willReturn(mockTreeableArgParent);
		given(mockTreeableArgParent.getTreeModule().getParent()).willReturn(expectedTreeableRoot);
		given(expectedTreeableRoot.getTreeModule().getParent()).willReturn(null);

		// When
		Treeable actualRootTreeable = recursiveTreeTraverserImpl.getRootEventTreeable(Treeable.class, mockTreeableArg);

		// Then
		assertEquals(expectedTreeableRoot, actualRootTreeable);

	}
	
	@Test
	public void WHEN_getRootTree_SHOULD_get_root_tree_GIVEN_1_level_tree() {
		// Given
		Treeable mockTreeableArg = mock(Treeable.class, RETURNS_DEEP_STUBS);
		given(mockTreeableArg.getTreeModule().getParent()).willReturn(null);

		// When
		Treeable actualRootTreeable = recursiveTreeTraverserImpl.getRootEventTreeable(Treeable.class, mockTreeableArg);

		// Then
		assertEquals(mockTreeableArg, actualRootTreeable);

	}


}
