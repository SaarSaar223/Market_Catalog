public interface IRBTree<T extends Comparable<T>> extends SortedCollectionInterface<T>,
        Iterable<T> {
  T remove(T data);
}
