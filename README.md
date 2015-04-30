# prefix_span
java -jar target/prefix_span-1.0-SNAPSHOT.jar spmf run PrefixSpan_AGP dataset/D5C3T3N26ascii.dataspmf testout 0.5
java -jar target/prefix_span-1.0-SNAPSHOT.jar spmf run PrefixSpan dataset/D5C3T3N26ascii.dataspmf testout 0.5
java -jar target/prefix_span-1.0-SNAPSHOT.jar spmf run SPAM dataset/D5C3T3N26ascii.dataspmf testout 0.5
java -jar target/prefix_span-1.0-SNAPSHOT.jar mypre dataset/D5C3T3N26ascii.datamypre 0.5 -1 -1
java -jar target/prefix_span-1.0-SNAPSHOT.jar prefixspan dataset/D5C3T3N26ascii.dataS 1 26 1
# 64bit
java -Xmx2048m -Xms1536m -XX:MaxHeapSize=256M -jar target/prefix_span-1.0-SNAPSHOT.jar spmf run PrefixSpan dataset/D5C3T3N26ascii.dataspmf testout 0.5
