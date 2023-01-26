package bg.autohouse.spider.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Page<T> implements Iterable<T> {
  private final List<T> content = new ArrayList<>();
  private final Pagination pagination;

  public Page(List<T> content, Pagination pagination) {
    this.content.addAll(content);
    this.pagination = pagination;
  }

  public List<T> getContent() {
    return Collections.unmodifiableList(content);
  }

  public Pagination getPagination() {
    return pagination;
  }

  public Iterator<T> iterator() {
    return content.iterator();
  }

  public Stream<T> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

    return this.stream().map(converter).collect(Collectors.toList());
  }

  public <U> Page<U> map(Function<? super T, ? extends U> converter) {
    return new Page<>(getConvertedContent(converter), getPagination());
  }
}
