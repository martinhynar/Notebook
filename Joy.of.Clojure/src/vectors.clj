;; gorilla-repl.fileformat = 1

;; **
;;; # Vectors
;;; 
;;; Vectors can be created in several ways
;;; * Using `(vector)`
;; **

;; @@
(ns vectors)

(vector 1 2 3 5)
;; @@

;; **
;;; Or using special syntax `[]`
;; **

;; @@
[1 2 5 9 8]
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"}],"value":"[1 2 5 9 8]"}
;; <=

;; @@

;; @@

;; @@

;; @@
