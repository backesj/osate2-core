package org.osate.annexsupport;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

public class AnnexContentAssistProxy extends AnnexProxy implements
		AnnexContentAssist {

	
	/** The annex resolver instance. */
	private AnnexContentAssist contentAssist = null;
	
	protected AnnexContentAssistProxy(IConfigurationElement configElem) {
		super(configElem);
		// TODO Auto-generated constructor stub
	}

	private AnnexContentAssist getContentAssist() {
		if (contentAssist != null) {
			return contentAssist;
		}
		try {
			contentAssist = (AnnexContentAssist) configElem.createExecutableExtension(ATT_CLASS);
		} catch (Exception e) {
			AnnexPlugin.logError("Failed to instantiate " + annexName + " content assist " + className + " in type: " + id
					+ " in plugin " + configElem.getDeclaringExtension().getContributor().getName(), e);
		}
		return contentAssist;
	}

	@Override
	public void callAnnexContentAssist(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		contentAssist = getContentAssist();
		contentAssist.callAnnexContentAssist(model, assignment, context, acceptor);
		
	}

}
