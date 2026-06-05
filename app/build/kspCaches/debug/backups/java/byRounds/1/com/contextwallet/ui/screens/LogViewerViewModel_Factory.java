package com.contextwallet.ui.screens;

import com.contextwallet.util.DebugLogger;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class LogViewerViewModel_Factory implements Factory<LogViewerViewModel> {
  private final Provider<DebugLogger> loggerProvider;

  public LogViewerViewModel_Factory(Provider<DebugLogger> loggerProvider) {
    this.loggerProvider = loggerProvider;
  }

  @Override
  public LogViewerViewModel get() {
    return newInstance(loggerProvider.get());
  }

  public static LogViewerViewModel_Factory create(
      javax.inject.Provider<DebugLogger> loggerProvider) {
    return new LogViewerViewModel_Factory(Providers.asDaggerProvider(loggerProvider));
  }

  public static LogViewerViewModel_Factory create(Provider<DebugLogger> loggerProvider) {
    return new LogViewerViewModel_Factory(loggerProvider);
  }

  public static LogViewerViewModel newInstance(DebugLogger logger) {
    return new LogViewerViewModel(logger);
  }
}
