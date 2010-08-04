-- generated by hosc0 from examples/exp/fo_dsl_2_a.hs

data Nat  = Z  | S Nat;
data Val  = Error  | N Nat | F (Val->Val);
data VarName  = VZ  | VS VarName;
data Exp  = NatZ  | NatS Exp | Var VarName | App Exp Exp | Lam VarName Exp | Fix VarName Exp;
data Env  = Empty  | Bind VarName Val Env;
data Bool  = True  | False ;

case  (F (\y25-> case  y25  of { Error  -> Error; N s16 -> Error; F w11 -> (w11 (F w11)); }))  of {
  Error  -> Error;
  F y5 -> (y5 (F y5));
  N x -> Error;
}
