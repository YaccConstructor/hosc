-- generated by higher-level SC from hl/or_even_odd.hs

data Bool  = True  | False ;
data Nat  = Z  | S Nat;

(letrec f=(\w1-> case  w1  of { Z  -> True; S v1 -> case  v1  of { Z  -> True; S r -> (f r); }; }) in (f n))
