/*
 *
 * <copyright>
 * Copyright  2012 by Carnegie Mellon University, all rights reserved.
 *
 * Use of the Open Source AADL Tool Environment (OSATE) is subject to the terms of the license set forth
 * at http://www.eclipse.org/org/documents/epl-v10.html.
 *
 * NO WARRANTY
 *
 * ANY INFORMATION, MATERIALS, SERVICES, INTELLECTUAL PROPERTY OR OTHER PROPERTY OR RIGHTS GRANTED OR PROVIDED BY
 * CARNEGIE MELLON UNIVERSITY PURSUANT TO THIS LICENSE (HEREINAFTER THE "DELIVERABLES") ARE ON AN "AS-IS" BASIS.
 * CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED AS TO ANY MATTER INCLUDING,
 * BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR A PARTICULAR PURPOSE, MERCHANTABILITY, INFORMATIONAL CONTENT,
 * NONINFRINGEMENT, OR ERROR-FREE OPERATION. CARNEGIE MELLON UNIVERSITY SHALL NOT BE LIABLE FOR INDIRECT, SPECIAL OR
 * CONSEQUENTIAL DAMAGES, SUCH AS LOSS OF PROFITS OR INABILITY TO USE SAID INTELLECTUAL PROPERTY, UNDER THIS LICENSE,
 * REGARDLESS OF WHETHER SUCH PARTY WAS AWARE OF THE POSSIBILITY OF SUCH DAMAGES. LICENSEE AGREES THAT IT WILL NOT
 * MAKE ANY WARRANTY ON BEHALF OF CARNEGIE MELLON UNIVERSITY, EXPRESS OR IMPLIED, TO ANY PERSON CONCERNING THE
 * APPLICATION OF OR THE RESULTS TO BE OBTAINED WITH THE DELIVERABLES UNDER THIS LICENSE.
 *
 * Licensee hereby agrees to defend, indemnify, and hold harmless Carnegie Mellon University, its trustees, officers,
 * employees, and agents from all claims or demands made against them (and any related losses, expenses, or
 * attorney's fees) arising out of, or relating to Licensee's and/or its sub licensees' negligent use or willful
 * misuse of or negligent conduct or willful misconduct regarding the Software, facilities, or other rights or
 * assistance granted by Carnegie Mellon University under this License, including, but not limited to, any claims of
 * product liability, personal injury, death, damage to property, or violation of any laws or regulations.
 *
 * Carnegie Mellon Carnegie Mellon University Software Engineering Institute authored documents are sponsored by the U.S. Department
 * of Defense under Contract F19628-00-C-0003. Carnegie Mellon University retains copyrights in all material produced
 * under this contract. The U.S. Government retains a non-exclusive, royalty-free license to publish or reproduce these
 * documents, or allow others to do so, for U.S. Government purposes only pursuant to the copyright license
 * under the contract clause at 252.227.7013.
 * </copyright>
 */
package org.osate.xtext.aadl2.serializer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.FlowEnd;
import org.osate.aadl2.FlowImplementation;
import org.osate.aadl2.FlowKind;
import org.osate.aadl2.modelsupport.util.AadlUtil;

public class Aadl2SyntacticSequencer extends AbstractAadl2SyntacticSequencer {

	@Override
	protected String getIDToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (semanticObject instanceof Classifier) {
			return ((Classifier) semanticObject).getName();
		}
		if (semanticObject instanceof AadlPackage) {
			return ((AadlPackage) semanticObject).getName();
		}
		return super.getIDToken(semanticObject, ruleCall, node);
	}

	@Override
	protected String getFULLINAMEToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (semanticObject instanceof ComponentImplementation) {
			return ((ComponentImplementation) semanticObject).getName();
		}
		return super.getFULLINAMEToken(semanticObject, ruleCall, node);
	}

// TODO phf: if we use PNAME at the end we need to fix the
	@Override
	protected String getPNAMEToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (semanticObject instanceof AadlPackage) {
			return ((AadlPackage) semanticObject).getName();
		}
		return super.getPNAMEToken(semanticObject, ruleCall, node);
	}

	@Override
	protected String getFLOWOUTToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (semanticObject instanceof FlowImplementation) {
			FlowEnd outend = ((FlowImplementation) semanticObject).getSpecification().getOutEnd();
			FlowEnd inend = ((FlowImplementation) semanticObject).getSpecification().getInEnd();

			// For some reason this can be called when handling Flow Sink Implementations. In that case, return the name of the in end unless there actually is
// an out end set.
			if (((FlowImplementation) semanticObject).getKind() == FlowKind.SINK) {
				if (outend == null && inend != null) {
					return AadlUtil.getFlowEndName(inend);
				}
				return AadlUtil.getFlowEndName(outend);
			}
			String head = (((FlowImplementation) semanticObject).getKind() == FlowKind.PATH
					&& ((FlowImplementation) semanticObject).getOwnedFlowSegments().isEmpty() ? AadlUtil
					.getFlowEndName(inend) + " -> " : "");
			return head + AadlUtil.getFlowEndName(outend);
		}
		return super.getFLOWOUTToken(semanticObject, ruleCall, node);
	}

	@Override
	protected String getFLOWINToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (semanticObject instanceof FlowImplementation) {
			FlowEnd inend = ((FlowImplementation) semanticObject).getSpecification().getInEnd();
//			FlowEnd outend = ((FlowImplementation)semanticObject).getSpecification().getOutEnd();
//			String tail = (((FlowImplementation)semanticObject).getKind() == FlowKind.PATH && ((FlowImplementation)semanticObject).getOwnedFlowSegments().isEmpty()?" -> "+AadlUtil.getFlowEndName(outend):"");
			return AadlUtil.getFlowEndName(inend);
		}
		return super.getFLOWINToken(semanticObject, ruleCall, node);
	}

}
