package com.contextwallet;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class ContextWalletApplication_MembersInjector implements MembersInjector<ContextWalletApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public ContextWalletApplication_MembersInjector(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<ContextWalletApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new ContextWalletApplication_MembersInjector(workerFactoryProvider);
  }

  public static MembersInjector<ContextWalletApplication> create(
      javax.inject.Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new ContextWalletApplication_MembersInjector(Providers.asDaggerProvider(workerFactoryProvider));
  }

  @Override
  public void injectMembers(ContextWalletApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.contextwallet.ContextWalletApplication.workerFactory")
  public static void injectWorkerFactory(ContextWalletApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
