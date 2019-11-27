package com.ecm.flowable.listener;

import org.flowable.job.api.JobInfo;
import org.flowable.job.service.JobServiceConfiguration;
import org.flowable.job.service.impl.asyncexecutor.AsyncExecutor;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 9:53 2018/4/25
 * @Modified By:
 */
public class AsyncListener implements AsyncExecutor {
    @Override
    public void start() {
        System.out.println("start");
    }

    @Override
    public void shutdown() {
        System.out.println("shutdown");
    }

    @Override
    public boolean executeAsyncJob(JobInfo jobInfo) {
        return false;
    }

    @Override
    public int getRemainingCapacity() {
        return 0;
    }

    @Override
    public void setJobServiceConfiguration(JobServiceConfiguration jobServiceConfiguration) {

    }

    @Override
    public JobServiceConfiguration getJobServiceConfiguration() {
        return null;
    }

    @Override
    public boolean isAutoActivate() {
        return false;
    }

    @Override
    public void setAutoActivate(boolean b) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public String getLockOwner() {
        return null;
    }

    @Override
    public int getTimerLockTimeInMillis() {
        return 0;
    }

    @Override
    public void setTimerLockTimeInMillis(int i) {

    }

    @Override
    public int getAsyncJobLockTimeInMillis() {
        return 0;
    }

    @Override
    public void setAsyncJobLockTimeInMillis(int i) {

    }

    @Override
    public int getDefaultTimerJobAcquireWaitTimeInMillis() {
        return 0;
    }

    @Override
    public void setDefaultTimerJobAcquireWaitTimeInMillis(int i) {

    }

    @Override
    public int getDefaultAsyncJobAcquireWaitTimeInMillis() {
        return 0;
    }

    @Override
    public void setDefaultAsyncJobAcquireWaitTimeInMillis(int i) {

    }

    @Override
    public int getDefaultQueueSizeFullWaitTimeInMillis() {
        return 0;
    }

    @Override
    public void setDefaultQueueSizeFullWaitTimeInMillis(int i) {

    }

    @Override
    public int getMaxAsyncJobsDuePerAcquisition() {
        return 0;
    }

    @Override
    public void setMaxAsyncJobsDuePerAcquisition(int i) {

    }

    @Override
    public int getMaxTimerJobsPerAcquisition() {
        return 0;
    }

    @Override
    public void setMaxTimerJobsPerAcquisition(int i) {

    }

    @Override
    public int getRetryWaitTimeInMillis() {
        return 0;
    }

    @Override
    public void setRetryWaitTimeInMillis(int i) {

    }

    @Override
    public int getResetExpiredJobsInterval() {
        return 0;
    }

    @Override
    public void setResetExpiredJobsInterval(int i) {

    }

    @Override
    public int getResetExpiredJobsPageSize() {
        return 0;
    }

    @Override
    public void setResetExpiredJobsPageSize(int i) {

    }
}
