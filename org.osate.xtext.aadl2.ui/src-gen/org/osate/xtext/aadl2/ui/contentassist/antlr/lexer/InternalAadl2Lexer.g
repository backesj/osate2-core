
/*
* generated by Xtext
*/
lexer grammar InternalAadl2Lexer;


@header {
package org.osate.xtext.aadl2.ui.contentassist.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;
}




KEYWORD_100 : 'implementation';

KEYWORD_99 : 'subcomponents';

KEYWORD_95 : 'aadlboolean';

KEYWORD_96 : 'aadlinteger';

KEYWORD_97 : 'connections';

KEYWORD_98 : 'enumeration';

KEYWORD_90 : 'aadlstring';

KEYWORD_91 : 'classifier';

KEYWORD_92 : 'properties';

KEYWORD_93 : 'prototypes';

KEYWORD_94 : 'subprogram';

KEYWORD_86 : 'parameter';

KEYWORD_87 : 'processor';

KEYWORD_88 : 'prototype';

KEYWORD_89 : 'reference';

KEYWORD_78 : 'aadlreal';

KEYWORD_79 : 'abstract';

KEYWORD_80 : 'constant';

KEYWORD_81 : 'features';

KEYWORD_82 : 'instance';

KEYWORD_83 : 'property';

KEYWORD_84 : 'provides';

KEYWORD_85 : 'requires';

KEYWORD_65 : 'applies';

KEYWORD_66 : 'compute';

KEYWORD_67 : 'extends';

KEYWORD_68 : 'feature';

KEYWORD_69 : 'inherit';

KEYWORD_70 : 'initial';

KEYWORD_71 : 'inverse';

KEYWORD_72 : 'package';

KEYWORD_73 : 'private';

KEYWORD_74 : 'process';

KEYWORD_75 : 'refined';

KEYWORD_76 : 'renames';

KEYWORD_77 : 'virtual';

KEYWORD_57 : 'access';

KEYWORD_58 : 'device';

KEYWORD_59 : 'memory';

KEYWORD_60 : 'public';

KEYWORD_61 : 'record';

KEYWORD_62 : 'source';

KEYWORD_63 : 'system';

KEYWORD_64 : 'thread';

KEYWORD_47 : 'annex';

KEYWORD_48 : 'calls';

KEYWORD_49 : 'delta';

KEYWORD_50 : 'event';

KEYWORD_51 : 'false';

KEYWORD_52 : 'flows';

KEYWORD_53 : 'group';

KEYWORD_54 : 'modes';

KEYWORD_55 : 'range';

KEYWORD_56 : 'units';

KEYWORD_34 : 'data';

KEYWORD_35 : 'flow';

KEYWORD_36 : 'from';

KEYWORD_37 : 'list';

KEYWORD_38 : 'mode';

KEYWORD_39 : 'none';

KEYWORD_40 : 'path';

KEYWORD_41 : 'port';

KEYWORD_42 : 'self';

KEYWORD_43 : 'sink';

KEYWORD_44 : 'true';

KEYWORD_45 : 'type';

KEYWORD_46 : 'with';

KEYWORD_24 : '+=>';

KEYWORD_25 : '<->';

KEYWORD_26 : ']->';

KEYWORD_27 : 'all';

KEYWORD_28 : 'and';

KEYWORD_29 : 'bus';

KEYWORD_30 : 'end';

KEYWORD_31 : 'not';

KEYWORD_32 : 'out';

KEYWORD_33 : 'set';

KEYWORD_14 : '->';

KEYWORD_15 : '-[';

KEYWORD_16 : '..';

KEYWORD_17 : '::';

KEYWORD_18 : '=>';

KEYWORD_19 : 'in';

KEYWORD_20 : 'is';

KEYWORD_21 : 'of';

KEYWORD_22 : 'or';

KEYWORD_23 : 'to';

KEYWORD_1 : '(';

KEYWORD_2 : ')';

KEYWORD_3 : '*';

KEYWORD_4 : '+';

KEYWORD_5 : ',';

KEYWORD_6 : '-';

KEYWORD_7 : '.';

KEYWORD_8 : ':';

KEYWORD_9 : ';';

KEYWORD_10 : '[';

KEYWORD_11 : ']';

KEYWORD_12 : '{';

KEYWORD_13 : '}';



RULE_ANNEXTEXT : '{**' ( options {greedy=false;} : . )*'**}';

RULE_SL_COMMENT : '--' ~(('\n'|'\r'))* ('\r'? '\n')?;

fragment RULE_EXPONENT : 'e' ('+'|'-')? RULE_DIGIT+;

fragment RULE_INT_EXPONENT : 'e' '+'? RULE_DIGIT+;

RULE_REAL_LIT : RULE_DIGIT+ ('_' RULE_DIGIT+)* '.' RULE_DIGIT+ ('_' RULE_DIGIT+)* RULE_EXPONENT?;

RULE_INTEGER_LIT : RULE_DIGIT+ ('_' RULE_DIGIT+)* ('#' RULE_BASED_INTEGER '#' RULE_INT_EXPONENT?|RULE_INT_EXPONENT?);

fragment RULE_BASE : RULE_DIGIT RULE_DIGIT?;

fragment RULE_DIGIT : '0'..'9';

fragment RULE_EXTENDED_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F');

fragment RULE_BASED_INTEGER : RULE_EXTENDED_DIGIT ('_'? RULE_EXTENDED_DIGIT)*;

RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_WS : (' '|'\t'|'\r'|'\n')+;


