-- generated by hosc0 from examples/sc/zip1.hs

data List a = Nil  | Cons a (List a);
data Pair a b = P a b;

(letrec
  f=(\r8->
    (\s8->
      case  r8  of {
        Nil  -> Nil;
        Cons x8 v1 -> case  s8  of { Nil  -> Nil; Cons u8 x -> (Cons (P (f1 x8) (f2 u8)) ((f v1) x)); };
      }))
in
  ((f l1) l2))
