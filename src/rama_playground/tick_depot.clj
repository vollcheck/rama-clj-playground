(ns rama-playground.tick-depot
  (:import
   (com.rpl.rama RamaModule)
   (com.rpl.rama.ops Ops)
   (com.rpl.rama.test InProcessCluster LaunchConfig)))


(deftype TickDepotModule []
  RamaModule
  (define [_ setup topologies]
    (.declareTickDepot setup "*ticks" 3000)

    (let [s (.stream topologies "s")]
      (-> s
          (.source "*ticks")
          (.each Ops/PRINTLN "Tick")))))

(defn -main []
  (with-open [cluster (InProcessCluster/create)]
    (.launchModule cluster (->TickDepotModule) (LaunchConfig. 4 4))
    (Thread/sleep 10000)))

(comment
  (-main)
  )
