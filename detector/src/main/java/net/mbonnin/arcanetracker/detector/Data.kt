package net.mbonnin.arcanetracker.detector

val RANKS = arrayOf(
        doubleArrayOf(26.991809844970703, 13.239742279052734, 0.5145149230957031, 23.415447235107422, -7.617626190185547, -36.377525329589844, 17.025390625, -7.065711975097656, 20.28607940673828, -8.301048278808594, -101.67217254638672, 14.409889221191406, -12.3841552734375, 10.518730163574219, -5.9898681640625),
        doubleArrayOf(10.63448715209961, 6.163486480712891, -0.7989082336425781, 22.35458755493164, 3.380687713623047, -48.93507385253906, 5.9105682373046875, -12.650192260742188, 3.4278564453125, 1.6673736572265625, -73.13500595092773, 8.20498275756836, -13.63742446899414, 15.096561431884766, 0.09338760375976562),
        doubleArrayOf(-45.56168746948242, -3.4906654357910156, 3.436962127685547, -9.60036849975586, -5.413249969482422, -52.18998718261719, -4.5037689208984375, -0.061065673828125, 4.2608184814453125, -5.009765625, -61.27501678466797, -5.0952301025390625, -1.8837509155273438, 33.05769348144531, -6.247161865234375),
        doubleArrayOf(-17.805965423583984, -15.62161636352539, -6.849910736083984, -22.30429458618164, 1.7322654724121094, -79.0139389038086, -11.169929504394531, -2.5262832641601562, -24.630393981933594, -1.2098388671875, -115.01140213012695, -2.247913360595703, -1.1465721130371094, -7.385829925537109, -1.2890129089355469),
        doubleArrayOf(-7.064292907714844, 14.051666259765625, 4.449676513671875, 0.21840667724609375, -1.6270751953125, -16.91424560546875, 17.064285278320312, 3.0719985961914062, 9.063987731933594, -1.41180419921875, -23.765125274658203, 17.760395050048828, 0.6624183654785156, 32.90253829956055, 2.7851829528808594),
        doubleArrayOf(9.944339752197266, 26.144184112548828, -18.73691177368164, -10.409534454345703, -13.24947738647461, -11.87103271484375, 28.407936096191406, -8.032623291015625, 9.212867736816406, -9.376708984375, -92.59928512573242, 11.77701187133789, -2.761089324951172, 10.18545150756836, 3.7621803283691406),
        doubleArrayOf(-43.03477096557617, 29.97769546508789, -2.307178497314453, -24.577922821044922, 2.735942840576172, -49.151973724365234, 20.824657440185547, 6.461475372314453, -11.785602569580078, -3.7784385681152344, 15.837039947509766, 43.896358489990234, 6.467838287353516, 29.78139877319336, -4.939937591552734),
        doubleArrayOf(-8.71749496459961, -0.9409675598144531, -17.33279037475586, 19.444469451904297, -2.4246864318847656, -15.568153381347656, 5.307830810546875, -15.526779174804688, 9.0584716796875, -3.3314132690429688, -37.06257629394531, 10.09075927734375, -11.029029846191406, -5.5608673095703125, -0.846282958984375),
        doubleArrayOf(-26.64303207397461, 4.966022491455078, 15.32309341430664, -3.8589210510253906, 4.564853668212891, -12.899261474609375, 14.304786682128906, -5.840705871582031, 9.568977355957031, 5.02996826171875, -19.683101654052734, 17.091999053955078, -11.853473663330078, 6.616802215576172, 4.859996795654297),
        doubleArrayOf(3.34710693359375, 2.7843856811523438, -30.174888610839844, 13.009269714355469, 2.4461135864257812, -12.530921936035156, 4.188255310058594, -22.971298217773438, 7.8290557861328125, 2.2031402587890625, -36.98627853393555, 10.393230438232422, -8.321392059326172, 2.1256065368652344, 2.138317108154297),
        doubleArrayOf(3.8167457580566406, -0.7885551452636719, -6.281658172607422, 7.885807037353516, -0.9036064147949219, -42.812320709228516, -0.6647377014160156, -3.2973976135253906, -4.285381317138672, -0.2744407653808594, -52.08612823486328, 6.8626861572265625, 2.628570556640625, -7.821586608886719, 4.0717620849609375),
        doubleArrayOf(-21.739612579345703, 5.336788177490234, 1.0174903869628906, 29.199993133544922, -0.24040603637695312, -43.55508804321289, 7.700687408447266, 9.10793685913086, 11.27969741821289, -0.9338188171386719, -58.71427917480469, 8.129547119140625, 10.371269226074219, 1.2367019653320312, 0.40464019775390625),
        doubleArrayOf(-4.4706268310546875, 37.816986083984375, -1.6814804077148438, 7.745597839355469, 5.673179626464844, -21.613201141357422, 33.041255950927734, -2.5639686584472656, 10.483966827392578, 5.740856170654297, -58.223087310791016, 25.88814926147461, -0.22723007202148438, -3.694499969482422, 2.6096153259277344),
        doubleArrayOf(-4.053714752197266, -5.244014739990234, 5.706287384033203, 11.972156524658203, -2.7401161193847656, -29.82482147216797, -27.243850708007812, 0.88433837890625, 9.487739562988281, -2.995452880859375, 6.607460021972656, -8.775627136230469, 5.4562225341796875, 35.15193176269531, 0.20557403564453125),
        doubleArrayOf(-22.145606994628906, 5.095062255859375, -11.8426513671875, 11.654693603515625, -4.077354431152344, -22.803302764892578, 9.747562408447266, -10.199337005615234, 20.473987579345703, -1.7137718200683594, -54.818397521972656, 7.5645294189453125, 5.5569000244140625, -0.11446380615234375, 7.1300048828125),
        doubleArrayOf(19.211898803710938, 29.13970947265625, -9.364212036132812, 22.95294952392578, -3.2512283325195312, -14.440879821777344, 40.273475646972656, -5.6191253662109375, 9.142173767089844, -6.066459655761719, -57.29435729980469, 41.76403045654297, 4.464088439941406, -7.789421081542969, -8.291900634765625),
        doubleArrayOf(-28.08202362060547, -6.0765533447265625, 8.121368408203125, 10.38543701171875, 3.9619903564453125, -70.64395523071289, -8.03292465209961, 6.501140594482422, 0.5984077453613281, 1.8713264465332031, -27.935657501220703, -4.198513031005859, -0.5572166442871094, 7.538341522216797, 6.709392547607422),
        doubleArrayOf(-24.243865966796875, -19.70014190673828, -14.433128356933594, 5.482032775878906, 7.615211486816406, -46.48236083984375, -20.979820251464844, -11.544166564941406, 3.8948211669921875, 3.4963531494140625, -45.500328063964844, -20.559425354003906, -7.3713226318359375, 0.6877059936523438, 0.6351852416992188),
        doubleArrayOf(-21.00330352783203, -0.7399749755859375, 5.446990966796875, 40.471168518066406, 9.782829284667969, -27.183334350585938, -0.9422073364257812, 6.443458557128906, 19.576309204101562, 8.261749267578125, -19.132110595703125, -0.9942626953125, 5.2935943603515625, 5.962425231933594, 9.754310607910156),
        doubleArrayOf(-22.694114685058594, 0.2424774169921875, 10.130897521972656, -5.795989990234375, 1.5902099609375, -45.19548034667969, 3.5470352172851562, -1.0816192626953125, 6.447845458984375, -3.3589401245117188, -53.705474853515625, -0.6422882080078125, -4.801231384277344, 7.84771728515625, -5.260650634765625),
        doubleArrayOf(-24.500518798828125, -5.8947296142578125, -17.851119995117188, 10.34442138671875, -3.632568359375, -57.44307327270508, -10.236705780029297, -9.706417083740234, 7.071109771728516, -3.753711700439453, -33.949928283691406, -9.292839050292969, -9.015693664550781, 20.257064819335938, 1.8032073974609375),
        doubleArrayOf(-3.2708969116210938, 29.764434814453125, 17.048011779785156, -14.004592895507812, 4.078056335449219, -40.01298522949219, 25.99242401123047, 4.890449523925781, -25.723968505859375, 0.5794830322265625, -58.17079162597656, 22.019546508789062, 9.420921325683594, -11.304641723632812, -2.8516616821289062),
        doubleArrayOf(-11.628036499023438, 8.336029052734375, -12.090278625488281, -4.1460113525390625, 6.0090179443359375, -20.871936798095703, 18.551578521728516, -13.60220718383789, 3.7841758728027344, 10.613597869873047, -65.31926727294922, 6.8812103271484375, -10.344207763671875, 1.3951950073242188, 3.4901657104492188),
        doubleArrayOf(-4.3218536376953125, -23.729171752929688, -22.802391052246094, -28.154129028320312, 1.189453125, -32.8148307800293, -17.684490203857422, -19.164546966552734, -25.666019439697266, -0.4182701110839844, -64.37825393676758, -8.826770782470703, -17.155941009521484, -18.09616470336914, -0.050754547119140625),
        doubleArrayOf(-6.892669677734375, 8.587265014648438, -1.0453414916992188, -10.317985534667969, 1.7261810302734375, -5.4532623291015625, 1.684539794921875, -0.5947799682617188, -1.2595138549804688, 4.272178649902344, -51.553993225097656, -10.896186828613281, -2.0270538330078125, -15.452133178710938, -0.2801513671875),
        doubleArrayOf(3.865825653076172, 8.07187271118164, -11.660045623779297, 16.764720916748047, -6.217151641845703, -13.945510864257812, 17.70391082763672, -12.261543273925781, -1.676055908203125, -2.71832275390625, -40.60419464111328, 18.438140869140625, -11.304328918457031, -8.859031677246094, -0.10163116455078125)
)

val FORMATS = arrayOf(
        doubleArrayOf(10.254478454589844,14.436492919921875,-5.4172515869140625,8.897857666015625,-23.038589477539062,-11.475936889648438,13.778434753417969,-5.802978515625,7.013298034667969,-21.621864318847656,-46.5200309753418,10.924694061279297,-5.109203338623047,3.791309356689453,-16.27975845336914),
        doubleArrayOf(63.8475341796875,10.263694763183594,0.42618560791015625,-1.5002365112304688,-7.3163909912109375,39.777992248535156,9.903945922851562,0.29803466796875,-1.0302963256835938,-8.316123962402344,-14.413352966308594,11.115852355957031,-3.192779541015625,1.1746826171875,-5.1017303466796875)
)

val MODES = arrayOf(
        doubleArrayOf(-41.76513671875,2.60595703125,-7.1806640625,0.0,-26.643798828125,-11.28759765625,-1.065673828125,-65.240478515625,15.66748046875,-20.30859375,17.0028076171875,-3.0826416015625,-76.9818115234375,17.6182861328125,-2.7254638671875),
        doubleArrayOf(-33.2177734375,3.704833984375,-14.261474609375,5.767333984375,-25.3955078125,-79.7745361328125,2.2027587890625,-21.4830322265625,9.3865966796875,-18.0164794921875,-110.66015625,0.57666015625,-6.056640625,4.875,-13.106201171875)
)