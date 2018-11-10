grammar Expression;

calc
    : expr=expression EOF
    ;

expression
    :     literal=Literal
        | '(' exp=expression ')'
        | <assoc=left> left=expression (op=MUL | op=DIV | op=MOD) right=expression
        | <assoc=left> left=expression (op=PLUS | op=MINUS) right=expression
        | left=expression (op=LE | op=GR | op=GEQ | op=LEQ) right=expression
        | left=expression ( op=EQ | op=NEQ) right=expression
        | left=expression op=AND right=expression
        | left=expression op=OR right=expression
    ;

Identifier
    :
        ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
    ;

Literal
    :   '-'? (('1'..'9') ('0'..'9')* | '0')
    ;

WHITESPACE : (' ' | '\t' | '\r'| '\n' | '//' (.)*? '\n') -> skip;

PLUS: '+';
MUL: '*';
MINUS: '-';
DIV: '/';
MOD: '%';
AND: '&&';
OR: '||';
LE: '<';
GR: '>';
LEQ: '<=';
GEQ: '>=';
EQ: '==';
NEQ: '!=';
