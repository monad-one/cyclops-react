package cyclops.typeclasses.functions;

import com.oath.cyclops.hkt.Higher;
import cyclops.async.Future;
import cyclops.collections.immutable.LinkedListX;
import cyclops.collections.immutable.PersistentQueueX;
import cyclops.collections.immutable.VectorX;
import cyclops.collections.mutable.DequeX;
import cyclops.collections.mutable.ListX;
import cyclops.collections.mutable.QueueX;
import cyclops.collections.mutable.SetX;
import cyclops.companion.CompletableFutures;
import cyclops.companion.Optionals.OptionalKind;
import cyclops.companion.Streams;
import cyclops.control.Ior;
import cyclops.control.Maybe;
import cyclops.control.Try;
import cyclops.control.Either;
import cyclops.monads.Witness.*;
import cyclops.reactive.ReactiveSeq;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


public class MonoidKs {


    public static <T>  MonoidK<optional,T> optionalPresent() {
        return  MonoidK.of(OptionalKind.empty(),SemigroupKs.optionalPresent());
    }
    public static <T> MonoidK<list,T> listXConcat() {
        return MonoidK.of(ListX.empty(),SemigroupKs.listXConcat());
    }




    /**
     * @return A combiner for SetX (concatenates two SetX into a single SetX)
     */
    static <T> MonoidK<set,T> setXConcat() {
        return MonoidK.of(SetX.empty(),SemigroupKs.setXConcat());
    }

    /**
     * @return A combiner for SortedSetX (concatenates two SortedSetX into a single SortedSetX)

    static <T> MonoidK<sortedSet,T> sortedSetXConcat() {
        return (a, b) -> SortedSetX.narrowK(a).insertAt(SortedSetX.narrowK(b));
    }*/

    /**
     * @return A combiner for QueueX (concatenates two QueueX into a single QueueX)
     */
    static <T> MonoidK<queue,T> queueXConcat() {
        return MonoidK.of(QueueX.empty(),SemigroupKs.queueXConcat());
    }

    /**
     * @return A combiner for DequeX (concatenates two DequeX into a single DequeX)
     */
    static <T> MonoidK<deque,T> dequeXConcat() {
        return MonoidK.of(DequeX.empty(),SemigroupKs.dequeXConcat());
    }

    /**
     * @return A combiner for LinkedListX (concatenates two LinkedListX into a single LinkedListX)
     */
    static <T> MonoidK<linkedListX,T> linkedListXConcat() {
        return MonoidK.of(LinkedListX.empty(),SemigroupKs.linkedListXConcat());
    }

    /**
     * @return A combiner for VectorX (concatenates two VectorX into a single VectorX)
     */
    static <T> MonoidK<vectorX,T> vectorXConcat() {
        return MonoidK.of(VectorX.empty(),SemigroupKs.vectorXConcat());
    }

    /**
     * @return A combiner for PersistentSetX (concatenates two PersistentSetX into a single PersistentSetX)

    static <T> MonoidK<persistentSetX,T> persistentSetXConcat() {
        return (a, b) -> PersistentSetX.narrowK(a).insertAt(PersistentSetX.narrowK(b));
    }
     */
    /**
     * @return A combiner for OrderedSetX (concatenates two OrderedSetX into a single OrderedSetX)

    static <T> MonoidK<OrderedsetX,T> orderedSetXConcat() {
        return (a, b) -> OrderedSetX.narrowK(a).insertAt(OrderedSetX.narrowK(b));
    }*/

    /**
     * @return A combiner for PersistentQueueX (concatenates two PersistentQueueX into a single PersistentQueueX)
     */
    static <T> MonoidK<persistentQueueX,T> persistentQueueXConcat() {
        return MonoidK.of(PersistentQueueX.empty(),SemigroupKs.persistentQueueXConcat());
    }






    /**
     * @return Combination of two ReactiveSeq Streams b is appended to a
     */
    static <T> MonoidK<reactiveSeq,T> combineReactiveSeq() {
        return MonoidK.of(ReactiveSeq.empty(),SemigroupKs.combineReactiveSeq());
    }

    static <T> MonoidK<reactiveSeq,T> firstNonEmptyReactiveSeq() {
        return MonoidK.of(ReactiveSeq.empty(),SemigroupKs.firstNonEmptyReactiveSeq());
    }
    static <T> MonoidK<reactiveSeq,T> ambReactiveSeq() {
        return MonoidK.of(ReactiveSeq.empty(),SemigroupKs.ambReactiveSeq());
    }

    static <T> MonoidK<reactiveSeq,T> mergeLatestReactiveSeq() {
        return MonoidK.of(ReactiveSeq.empty(),SemigroupKs.mergeLatestReactiveSeq());
    }



    /**
     * @return Combination of two Stream's : b is appended to a
     */
    static <T> MonoidK<stream,T> combineStream() {
        return MonoidK.of(Streams.StreamKind.widen(Stream.empty()),SemigroupKs.combineStream());
    }


    /**
     * @return Combine two CompletableFuture's by taking the first present
     */
    static <T> MonoidK<completableFuture,T> firstCompleteCompletableFuture() {
        return MonoidK.of(CompletableFutures.CompletableFutureKind.widen(new CompletableFuture<>()),SemigroupKs.firstCompleteCompletableFuture());
    }
    /**
     * @return Combine two Future's by taking the first result
     */
    static <T> MonoidK<future,T> firstCompleteFuture() {
            return MonoidK.of(Future.future(),SemigroupKs.firstCompleteFuture());
    }


    /**
     * @return Combine two Future's by taking the first successful
     */
    static <T> MonoidK<future,T> firstSuccessfulFuture() {
        return MonoidK.of(Future.future(),SemigroupKs.firstSuccessfulFuture());
    }
    /**
     * @return Combine two Xor's by taking the first right
     */
    static <ST,PT> MonoidK<Higher<either,ST>,PT> firstPrimaryXor(ST zero) {
        return MonoidK.of(Either.left(zero),SemigroupKs.firstPrimaryXor());
    }
    /**
     * @return Combine two Xor's by taking the first left
     */
    static <ST,PT> MonoidK<Higher<either,ST>,PT> firstSecondaryXor(PT zero) {
        return MonoidK.of(Either.right(zero),SemigroupKs.firstSecondaryXor());
    }
    /**
     * @return Combine two Xor's by taking the last right
     */
    static <ST,PT> MonoidK<Higher<either,ST>,PT> lastPrimaryXor(ST zero) {
        return MonoidK.of(Either.left(zero),SemigroupKs.lastPrimaryXor());
    }
    /**
     * @return Combine two Xor's by taking the last left
     */
    static <ST,PT> MonoidK<Higher<either,ST>,PT> lastSecondaryXor(PT zero) {
        return MonoidK.of(Either.right(zero),SemigroupKs.lastSecondaryXor());
    }
    /**
     * @return Combine two Try's by taking the first right
     */
    static <T,X extends Throwable> MonoidK<Higher<tryType,X>,T> firstTrySuccess(X zero) {
        return MonoidK.of(Try.failure(zero),SemigroupKs.firstTrySuccess());
    }
    /**
     * @return Combine two Try's by taking the first left
     */
    static <T,X extends Throwable> MonoidK<Higher<tryType,X>,T> firstTryFailure(T zero) {
        return MonoidK.of(Try.success(zero),SemigroupKs.firstTryFailure());
    }
    /**
     * @return Combine two Tryr's by taking the last right
     */
    static<T,X extends Throwable> MonoidK<Higher<tryType,X>,T> lastTrySuccess(X zero) {
        return MonoidK.of(Try.failure(zero),SemigroupKs.lastTrySuccess());
    }
    /**
     * @return Combine two Try's by taking the last left
     */
    static <T,X extends Throwable> MonoidK<Higher<tryType,X>,T> lastTryFailure(T zero) {
        return MonoidK.of(Try.success(zero),SemigroupKs.lastTryFailure());
    }
    /**
     * @return Combine two Ior's by taking the first right
     */
    static <ST,PT> MonoidK<Higher<ior,ST>,PT> firstPrimaryIor(ST zero) {
        return MonoidK.of(Ior.left(zero),SemigroupKs.firstPrimaryIor());
    }
    /**
     * @return Combine two Ior's by taking the first left
     */
    static <ST,PT> MonoidK<Higher<ior,ST>,PT> firstSecondaryIor(PT zero) {
        return MonoidK.of(Ior.right(zero),SemigroupKs.firstSecondaryIor());
    }
    /**
     * @return Combine two Ior's by taking the last right
     */
    static <ST,PT> MonoidK<Higher<ior,ST>,PT> lastPrimaryIor(ST zero) {
        return MonoidK.of(Ior.left(zero),SemigroupKs.lastPrimaryIor());
    }
    /**
     * @return Combine two Ior's by taking the last left
     */
    static <ST,PT> MonoidK<Higher<ior,ST>,PT> lastSecondaryIor(PT zero) {
        return MonoidK.of(Ior.right(zero),SemigroupKs.lastSecondaryIor());
    }

    /**
     * @return Combine two Maybe's by taking the first present
     */
    static <T> MonoidK<maybe,T> firstPresentMaybe() {
        return MonoidK.of(Maybe.nothing(),SemigroupKs.firstPresentMaybe());
    }

    /**
     * @return Combine two optionals by taking the first present
     */
    static <T> MonoidK<optional,T> firstPresentOptional() {
        return MonoidK.of(OptionalKind.empty(),SemigroupKs.firstPresentOptional());
    }

    /**
     * @return Combine two Maybes by taking the last present
     */
    static <T> MonoidK<maybe,T> lastPresentMaybe() {
        return MonoidK.of(Maybe.nothing(),SemigroupKs.lastPresentMaybe());
    }

    /**
     * @return Combine two optionals by taking the last present
     */
    static <T> MonoidK<optional,T> lastPresentOptional() {
        return MonoidK.of(OptionalKind.empty(),SemigroupKs.lastPresentOptional());
    }
}
