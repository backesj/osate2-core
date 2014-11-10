/*
 *
 * <copyright>
 * Copyright  2014 by Carnegie Mellon University, all rights reserved.
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
package org.osate.core.tests.aadl2javavalidator

import org.eclipse.xtext.junit4.InjectWith
import org.eclipselabs.xtext.utils.unittesting.FluentIssueCollection
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2
import org.junit.Test
import org.junit.runner.RunWith
import org.osate.aadl2.AadlPackage
import org.osate.aadl2.AbstractImplementation
import org.osate.aadl2.PropertySet
import org.osate.aadl2.SubprogramImplementation
import org.osate.aadl2.UnitsType
import org.osate.core.test.Aadl2UiInjectorProvider
import org.osate.core.test.OsateTest

import static extension org.junit.Assert.assertEquals

@RunWith(XtextRunner2)
@InjectWith(Aadl2UiInjectorProvider)
class OtherAadl2JavaValidatorTest extends OsateTest {
	override getProjectName() {
		"Other_Aadl2_Java_Validator_Test"
	}
	
	//Tests checkFlowConnectionOrder
	@Test
	def void testFlowSegmentTypes() {
		createFiles("legalFlowSegmentsTypeTest.aadl" -> '''
			package legalFlowSegmentsTypeTest
			public
				abstract a1
				features
					af1: feature;
					da1: provides data access;
					dp1: in data port a2.i;
					edp1: in event data port a2.i;
					fg1: feature group fgt1;
				flows
					fpath1: flow path af1 -> af1;
					fpath2: flow path af1 -> af1;
					fpath3: flow path af1 -> af1;
					fpath4: flow path af1 -> af1;
					fsource1: flow source af1;
					fsource2: flow source af1;
					fsource3: flow source af1;
					fsource4: flow source af1;
					fsource5: flow source af1;
					fsource6: flow source af1;
					fsource7: flow source af1;
					fsource8: flow source af1;
					fsource9: flow source af1;
					fsource10: flow source af1;
					fsource11: flow source af1;
					fsource12: flow source af1;
					fsource13: flow source af1;
					fsource14: flow source af1;
					fsource15: flow source af1;
					fsource16: flow source af1;
					fsource17: flow source af1;
					fsource18: flow source af1;
					fsource19: flow source af1;
					fsource20: flow source af1;
					fsource21: flow source af1;
				end a1;
				
				abstract implementation a1.i
				subcomponents
					asub1: abstract a2.i;
				calls sequence1: {
					call1: subprogram subp1.i;
				};
				connections
					fconn1: feature af1 -> af1;
					fconn2: feature asub1.af2 -> af1;
				flows
					--Connection (at ConnectionFlow)
					fpath1: flow path af1 -> fconn1 -> af1;
					--DataAccess (at ConnectionFlow)
					fpath2: flow path af1 -> da1 -> af1;
					--FlowSpecification (at ConnectionFlow)
					fpath3: flow path af1 -> fpath1 -> af1;
					--Subcomponent (at ConnectionFlow)
					fpath4: flow path af1 -> asub1 -> af1;
					
					--Connection (at SubcomponentFlow)
					fsource1: flow source fconn1 -> fconn2 -> af1;
					--DataAccess (at SubcomponentFlow)
					fsource2: flow source da1 -> fconn2 -> af1;
					--FlowSpecification (at SubcomponentFlow)
					fsource3: flow source fsource1 -> fconn2 -> af1;
					--Subcomponent (at SubcomponentFlow)
					fsource4: flow source asub1 -> fconn2 -> af1;
					
					--DataPort.Connection
					fsource5: flow source dp1.fconn3 -> fconn2 -> af1;
					--DataPort.DataAccess
					fsource6: flow source dp1.da2 -> fconn2 -> af1;
					--DataPort.FlowSpecification
					fsource7: flow source dp1.fsource22 -> fconn2 -> af1;
					--DataPort.Subcomponent
					fsource8: flow source dp1.asub2 -> fconn2 -> af1;
					
					--EventDataPort.Connection
					fsource9: flow source edp1.fconn3 -> fconn2 -> af1;
					--EventDataPort.DataAccess
					fsource10: flow source edp1.da2 -> fconn2 -> af1;
					--EventDataPort.FlowSpecification
					fsource11: flow source edp1.fsource22 -> fconn2 -> af1;
					--EventDataPort.Subcomponent
					fsource12: flow source edp1.asub2 -> fconn2 -> af1;
					
					--FeatureGroup.DataAccess
					fsource13: flow source fg1.da4 -> fconn2 -> af1;
					
					--Subcomponent.Connection
					fsource14: flow source asub1.fconn3 -> fconn2 -> af1;
					--Subcomponent.DataAccess
					fsource15: flow source asub1.da2 -> fconn2 -> af1;
					--Subcomponent.FlowSpecification
					fsource16: flow source asub1.fsource22 -> fconn2 -> af1;
					--Subcomponent.Subcomponent
					fsource17: flow source asub1.asub2 -> fconn2 -> af1;
					
					--SubprogramCall.Connection
					fsource18: flow source call1.fconn4 -> fconn2 -> af1;
					--SubprogramCall.DataAccess
					fsource19: flow source call1.da3 -> fconn2 -> af1;
					--SubprogramCall.FlowSpecification
					fsource20: flow source call1.fsource23 -> fconn2 -> af1;
					--SubprogramCall.Subcomponent
					fsource21: flow source call1.asub3 -> fconn2 -> af1;
				end a1.i;
				
				abstract a2
				features
					af2: feature;
					da2: provides data access;
				flows
					fsource22: flow source af2;
				end a2;
				
				abstract implementation a2.i
				subcomponents
					asub2: abstract;
				connections
					fconn3: feature af2 -> af2;
				end a2.i;
				
				subprogram subp1
				features
					af3: feature;
					param1: in parameter a2.i;
					da3: requires data access;
				flows
					fsource23: flow source af3;
					fsource24: flow source af3;
					fsource25: flow source af3;
					fsource26: flow source af3;
				end subp1;
				
				subprogram implementation subp1.i
				subcomponents
					asub3: abstract a2;
				connections
					fconn4: feature asub3.af2 -> af3;
				flows
					--Parameter.Connection
					fsource23: flow source param1.asub2 -> fconn4 -> af3;
					--Parameter.DataAccess
					fsource24: flow source param1.da2 -> fconn4 -> af3;
					--Parameter.FlowSpecification
					fsource25: flow source param1.fsource22 -> fconn4 -> af3;
					--Parameter.Subcomponent
					fsource26: flow source param1.asub2 -> fconn4 -> af3;
				end subp1.i;
				
				feature group fgt1
				features
					da4: provides data access;
				end fgt1;
			end legalFlowSegmentsTypeTest;
		''')
		suppressSerialization
		val testFileResult = testFile("legalFlowSegmentsTypeTest.aadl")
		val issueCollection = new FluentIssueCollection(testFileResult.resource, newArrayList, newArrayList)
		testFileResult.resource.contents.head as AadlPackage => [
			"legalFlowSegmentsTypeTest".assertEquals(name)
			publicSection.ownedClassifiers.get(1) as AbstractImplementation => [
				"a1.i".assertEquals(name)
				ownedFlowImplementations.get(1) => [
					"fpath2".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"da1".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Connection, found Data Access 'da1'")
					]
				]
				ownedFlowImplementations.get(2) => [
					"fpath3".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"fpath1".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Connection, found Flow Specification 'fpath1'")
					]
				]
				ownedFlowImplementations.get(3) => [
					"fpath4".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"asub1".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Connection, found Abstract Subcomponent 'asub1'")
					]
				]
				ownedFlowImplementations.get(4) => [
					"fsource1".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"fconn1".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Feature Connection 'fconn1'")
					]
				]
				ownedFlowImplementations.get(6) => [
					"fsource3".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"fsource1".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Flow Specification 'fsource1'")
					]
				]
				ownedFlowImplementations.get(8) => [
					"fsource5".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"dp1".assertEquals(context.name)
						"fconn3".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Data Port.Feature Connection 'dp1.fconn3'")
					]
				]
				ownedFlowImplementations.get(9) => [
					"fsource6".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"dp1".assertEquals(context.name)
						"da2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Data Port.Data Access 'dp1.da2'")
					]
				]
				ownedFlowImplementations.get(10) => [
					"fsource7".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"dp1".assertEquals(context.name)
						"fsource22".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Data Port.Flow Specification 'dp1.fsource22'")
					]
				]
				ownedFlowImplementations.get(11) => [
					"fsource8".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"dp1".assertEquals(context.name)
						"asub2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Data Port.Abstract Subcomponent 'dp1.asub2'")
					]
				]
				ownedFlowImplementations.get(12) => [
					"fsource9".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"edp1".assertEquals(context.name)
						"fconn3".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Event Data Port.Feature Connection 'edp1.fconn3'")
					]
				]
				ownedFlowImplementations.get(13) => [
					"fsource10".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"edp1".assertEquals(context.name)
						"da2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Event Data Port.Data Access 'edp1.da2'")
					]
				]
				ownedFlowImplementations.get(14) => [
					"fsource11".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"edp1".assertEquals(context.name)
						"fsource22".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Event Data Port.Flow Specification 'edp1.fsource22'")
					]
				]
				ownedFlowImplementations.get(15) => [
					"fsource12".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"edp1".assertEquals(context.name)
						"asub2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Event Data Port.Abstract Subcomponent 'edp1.asub2'")
					]
				]
				ownedFlowImplementations.get(16) => [
					"fsource13".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"fg1".assertEquals(context.name)
						"da4".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Feature Group.Data Access 'fg1.da4'")
					]
				]
				ownedFlowImplementations.get(17) => [
					"fsource14".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"asub1".assertEquals(context.name)
						"fconn3".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Abstract Subcomponent.Feature Connection 'asub1.fconn3'")
					]
				]
				ownedFlowImplementations.get(18) => [
					"fsource15".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"asub1".assertEquals(context.name)
						"da2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Abstract Subcomponent.Data Access 'asub1.da2'")
					]
				]
				ownedFlowImplementations.get(20) => [
					"fsource17".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"asub1".assertEquals(context.name)
						"asub2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Abstract Subcomponent.Abstract Subcomponent 'asub1.asub2'")
					]
				]
				ownedFlowImplementations.get(21) => [
					"fsource18".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"call1".assertEquals(context.name)
						"fconn4".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Subprogram Call.Feature Connection 'call1.fconn4'")
					]
				]
				ownedFlowImplementations.get(22) => [
					"fsource19".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"call1".assertEquals(context.name)
						"da3".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Subprogram Call.Data Access 'call1.da3'")
					]
				]
				ownedFlowImplementations.get(23) => [
					"fsource20".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"call1".assertEquals(context.name)
						"fsource23".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Subprogram Call.Flow Specification 'call1.fsource23'")
					]
				]
				ownedFlowImplementations.get(24) => [
					"fsource21".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"call1".assertEquals(context.name)
						"asub3".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Subprogram Call.Abstract Subcomponent 'call1.asub3'")
					]
				]
			]
			publicSection.ownedClassifiers.get(5) as SubprogramImplementation => [
				"subp1.i".assertEquals(name)
				ownedFlowImplementations.get(0) => [
					"fsource23".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"param1".assertEquals(context.name)
						"asub2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Parameter.Abstract Subcomponent 'param1.asub2'")
					]
				]
				ownedFlowImplementations.get(1) => [
					"fsource24".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"param1".assertEquals(context.name)
						"da2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Parameter.Data Access 'param1.da2'")
					]
				]
				ownedFlowImplementations.get(2) => [
					"fsource25".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"param1".assertEquals(context.name)
						"fsource22".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Parameter.Flow Specification 'param1.fsource22'")
					]
				]
				ownedFlowImplementations.get(3) => [
					"fsource26".assertEquals(specification.name)
					ownedFlowSegments.get(0) => [
						"param1".assertEquals(context.name)
						"asub2".assertEquals(flowElement.name)
						//Tests checkFlowConnectionOrder
						assertError(testFileResult.issues, issueCollection, "Expected Data Access, Subcomponent, or Subcomponent.Flow Specification; found Parameter.Abstract Subcomponent 'param1.asub2'")
					]
				]
			]
		]
		issueCollection.sizeIs(issueCollection.issues.size)
		assertConstraints(issueCollection)
	}
	
	//Tests typeCheckModeTransitionTrigger
	@Test
	def void testModeTransitionTriggerTypes() {
		createFiles("legalTypeTest.aadl" -> '''
			package legalTypeTest
			public
				abstract a1
				features
					af1: feature;
					dp1: in data port a2.i;
					edp1: in event data port a2.i;
					fg1: feature group fgt1;
				end a1;
				
				abstract implementation a1.i
				subcomponents
					asub1: abstract a2.i;
				internal features
					es1: event;
				processor features
					pp1: port;
				calls sequence1: {
					call1: subprogram subp1.i;
				};
				modes
					m1: initial mode;
					m2: mode;
					mt1: m1 -[
						--AbstractFeature
						af1,
						--InternalFeature
						self.es1,
						--Port
						dp1,
						--PortProxy
						processor.pp1,
						
						--DataPort.AbstractFeature
						dp1.af2,
						--DataPort.InternalFeature
						dp1.es2,
						--DataPort.Port
						dp1.ep1,
						--DataPort.PortProxy
						dp1.pp2,
						
						--EventDataPort.AbstractFeature
						edp1.af2,
						--EventDataPort.InternalFeature
						edp1.es2,
						--EventDataPort.Port
						edp1.ep1,
						--EventDataPort.PortProxy
						edp1.pp2,
						
						--FeatureGroup.AbstractFeature
						fg1.af3,
						--FeatureGroup.Port
						fg1.ep2,
						
						--Subcomponent.AbstractFeature
						asub1.af2,
						--Subcomponent.InternalFeature
						asub1.es2,
						--Subcomponent.Port
						asub1.ep1,
						--Subcomponent.PortProxy
						asub1.pp2,
						
						--SubprogramCall.AbstractFeature
						call1.af4,
						--SubprogramCall.InternalFeature
						call1.es3,
						--SubprogramCall.Port
						call1.ep3,
						--SubprogramCall.PortProxy
						call1.pp3
					]-> m2;
				end a1.i;
				
				abstract a2
				features
					af2: feature;
					ep1: in event port;
				end a2;
				
				abstract implementation a2.i
				internal features
					es2: event;
				processor features
					pp2: port;
				end a2.i;
				
				feature group fgt1
				features
					af3: feature;
					ep2: in event port;
				end fgt1;
				
				subprogram subp1
				features
					af4: feature;
					ep3: in event port;
					param1: in parameter a2.i;
				end subp1;
				
				subprogram implementation subp1.i
				internal features
					es3: event;
				processor features
					pp3: port;
				modes
					m3: initial mode;
					m4: mode;
					mt2: m3 -[
						--Parameter.AbstractFeature
						param1.af2,
						--Parameter.InternalFeature
						param1.es2,
						--Parameter.Port
						param1.ep1,
						--Parameter.PortProxy
						param1.pp2
					]-> m4;
				end subp1.i;
			end legalTypeTest;
		''')
		suppressSerialization
		val testFileResult = testFile("legalTypeTest.aadl")
		val issueCollection = new FluentIssueCollection(testFileResult.resource, newArrayList, newArrayList)
		testFileResult.resource.contents.head as AadlPackage => [
			"legalTypeTest".assertEquals(name)
			publicSection.ownedClassifiers.get(1) as AbstractImplementation => [
				"a1.i".assertEquals(name)
				ownedModeTransitions.head => [
					"mt1".assertEquals(name)
					ownedTriggers.get(4) => [
						"dp1".assertEquals(context.name)
						"af2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in a 'data port' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(5) => [
						"dp1".assertEquals(context.name)
						"es2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in a 'data port' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(6) => [
						"dp1".assertEquals(context.name)
						"ep1".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in a 'data port' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(7) => [
						"dp1".assertEquals(context.name)
						"pp2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in a 'data port' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(8) => [
						"edp1".assertEquals(context.name)
						"af2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in an 'event data port' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(9) => [
						"edp1".assertEquals(context.name)
						"es2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in an 'event data port' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(10) => [
						"edp1".assertEquals(context.name)
						"ep1".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in an 'event data port' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(11) => [
						"edp1".assertEquals(context.name)
						"pp2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in an 'event data port' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(15) => [
						"asub1".assertEquals(context.name)
						"es2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "An 'event source' in Abstract Subcomponent is not a valid mode transition trigger.")
					]
					ownedTriggers.get(17) => [
						"asub1".assertEquals(context.name)
						"pp2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "A 'port proxy' in Abstract Subcomponent is not a valid mode transition trigger.")
					]
					ownedTriggers.get(19) => [
						"call1".assertEquals(context.name)
						"es3".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "An 'event source' in Subprogram Call is not a valid mode transition trigger.")
					]
					ownedTriggers.get(21) => [
						"call1".assertEquals(context.name)
						"pp3".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "A 'port proxy' in Subprogram Call is not a valid mode transition trigger.")
					]
				]
			]
			publicSection.ownedClassifiers.get(6) as SubprogramImplementation => [
				"subp1.i".assertEquals(name)
				ownedModeTransitions.head => [
					"mt2".assertEquals(name)
					ownedTriggers.get(0) => [
						"param1".assertEquals(context.name)
						"af2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in a 'parameter' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(1) => [
						"param1".assertEquals(context.name)
						"es2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in a 'parameter' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(2) => [
						"param1".assertEquals(context.name)
						"ep1".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in a 'parameter' is not a valid mode transition trigger.")
					]
					ownedTriggers.get(3) => [
						"param1".assertEquals(context.name)
						"pp2".assertEquals(triggerPort.name)
						//Tests typeCheckModeTransitionTrigger
						assertError(testFileResult.issues, issueCollection, "Anything in a 'parameter' is not a valid mode transition trigger.")
					]
				]
			]
		]
		issueCollection.sizeIs(issueCollection.issues.size)
		assertConstraints(issueCollection)
	}
	
	//Tests caseUnitLiteral
	@Test
	def void testUnitLiterals() {
		createFiles("ps.aadl" -> '''
			property set ps is
				ut1: type units (ul1, ul2 => ul1 * 10, ul3 => ul4 * 10, ul4 => ul4 * 10);
			end ps;
		''')
		suppressSerialization
		val testFileResult = testFile("ps.aadl")
		val issueCollection = new FluentIssueCollection(testFileResult.resource, newArrayList, newArrayList)
		testFileResult.resource.contents.head as PropertySet => [
			"ps".assertEquals(name)
			ownedPropertyTypes.head as UnitsType => [
				"ut1".assertEquals(name)
				ownedLiterals.get(2) => [
					"ul3".assertEquals(name)
					//Tests caseUnitLiteral
					assertError(testFileResult.issues, issueCollection, "'ul4' is not declared before 'ul3'")
				]
				ownedLiterals.get(3) => [
					"ul4".assertEquals(name)
					//Tests caseUnitLiteral
					assertError(testFileResult.issues, issueCollection, "'ul4' cannot be its own base unit")
				]
			]
		]
		issueCollection.sizeIs(issueCollection.issues.size)
		assertConstraints(issueCollection)
	}
}