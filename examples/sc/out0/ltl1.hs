-- generated by hosc0 from examples/mc/ltl1.hs

data Bool  = True  | False ;
data State  = S Bool Bool;
data List a = Cons a (List a);

case 
(letrec
  f=(\s4->
    case  (qq s4)  of {
      True  ->
        case  (pp s4)  of {
          True  -> case  (f case  s4  of { Cons x4 y -> y; })  of { False  -> True; True  -> False; };
          False  -> False;
        };
      False  -> False;
    })
in
  (f states))
 of {
  False  -> True;
  True  -> False;
}
