package com.yang.newstest.bean;

import java.util.List;

//获取新闻后的实体类
public class NewsBean {
    private List<NewsBean.ChannelsBean> channels;
    private DocsBean docs;//新闻内容主要在此

    public List<NewsBean.ChannelsBean> getChannels() {
        return channels;
    }

    public void setChannels(List<NewsBean.ChannelsBean> channels) {
        this.channels = channels;
    }

    public DocsBean getDocs() {
        return docs;
    }

    public void setDocs(DocsBean docs) {
        this.docs = docs;
    }

    public static class DocsBean {
        //新闻内容
        private List<FocusesBean> focuses;//暂未知其含义
        private PagerBean pager;//暂未知其含义
        private List<ListBean> list;//新闻列表项，其中一共有三种列表视图，使用recyclerview进行展示时通过listStyle字段进行区分

        public List<FocusesBean> getFocuses() {
            return focuses;
        }

        public void setFocuses(List<FocusesBean> focuses) {
            this.focuses = focuses;
        }

        public PagerBean getPager() {
            return pager;
        }

        public void setPager(PagerBean pager) {
            this.pager = pager;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PagerBean {
            private int pageIndex;
            private int pageSize;
            private int pageCount;
            private int total;

            public int getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(int pageIndex) {
                this.pageIndex = pageIndex;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class FocusesBean {
            private String docId;
            private String docType;
            private String docUrl;
            private String focusImageTitle;
            private String focusImageUrl;
            private String pubTime;
            private String label;
            private String linkUrl;
            private String h5Url;
            private String shareUrl;
            private String commentSet;
            private String likeSet;

            public String getDocId() {
                return docId;
            }

            public void setDocId(String docId) {
                this.docId = docId;
            }

            public String getDocType() {
                return docType;
            }

            public void setDocType(String docType) {
                this.docType = docType;
            }

            public String getDocUrl() {
                return docUrl;
            }

            public void setDocUrl(String docUrl) {
                this.docUrl = docUrl;
            }

            public String getFocusImageTitle() {
                return focusImageTitle;
            }

            public void setFocusImageTitle(String focusImageTitle) {
                this.focusImageTitle = focusImageTitle;
            }

            public String getFocusImageUrl() {
                return focusImageUrl;
            }

            public void setFocusImageUrl(String focusImageUrl) {
                this.focusImageUrl = focusImageUrl;
            }

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getCommentSet() {
                return commentSet;
            }

            public void setCommentSet(String commentSet) {
                this.commentSet = commentSet;
            }

            public String getLikeSet() {
                return likeSet;
            }

            public void setLikeSet(String likeSet) {
                this.likeSet = likeSet;
            }
        }

        public static class ListBean {
            private String docId;
            private String docType;
            private String docUrl;
            private String docTitle;
            private String source;
            private String label;
            private String listStyle;
            private String listTitle;
            private List<String> imgUrls;
            private String cardImageUrl;
            private String pubTime;
            private String author;
            private String commentSet;
            private String likeSet;
            private LiveBean live;
            private VideoBean video;
            private AudioBean audio;
            private ChannelBean channel;
            private String linkUrl;
            private String h5Url;
            private String hasChildren;
            private String channelCode;
            private String shareUrl;

            public String getDocId() {
                return docId;
            }

            public void setDocId(String docId) {
                this.docId = docId;
            }

            public String getDocType() {
                return docType;
            }

            public void setDocType(String docType) {
                this.docType = docType;
            }

            public String getDocUrl() {
                return docUrl;
            }

            public void setDocUrl(String docUrl) {
                this.docUrl = docUrl;
            }

            public String getDocTitle() {
                return docTitle;
            }

            public void setDocTitle(String docTitle) {
                this.docTitle = docTitle;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getListStyle() {
                return listStyle;
            }

            public void setListStyle(String listStyle) {
                this.listStyle = listStyle;
            }

            public String getListTitle() {
                return listTitle;
            }

            public void setListTitle(String listTitle) {
                this.listTitle = listTitle;
            }

            public List<String> getImgUrls() {
                return imgUrls;
            }

            public void setImgUrls(List<String> imgUrls) {
                this.imgUrls = imgUrls;
            }

            public String getCardImageUrl() {
                return cardImageUrl;
            }

            public void setCardImageUrl(String cardImageUrl) {
                this.cardImageUrl = cardImageUrl;
            }

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getCommentSet() {
                return commentSet;
            }

            public void setCommentSet(String commentSet) {
                this.commentSet = commentSet;
            }

            public String getLikeSet() {
                return likeSet;
            }

            public void setLikeSet(String likeSet) {
                this.likeSet = likeSet;
            }

            public LiveBean getLive() {
                return live;
            }

            public void setLive(LiveBean live) {
                this.live = live;
            }

            public VideoBean getVideo() {
                return video;
            }

            public void setVideo(VideoBean video) {
                this.video = video;
            }

            public AudioBean getAudio() {
                return audio;
            }

            public void setAudio(AudioBean audio) {
                this.audio = audio;
            }

            public ChannelBean getChannel() {
                return channel;
            }

            public void setChannel(ChannelBean channel) {
                this.channel = channel;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

            public String getHasChildren() {
                return hasChildren;
            }

            public void setHasChildren(String hasChildren) {
                this.hasChildren = hasChildren;
            }

            public String getChannelCode() {
                return channelCode;
            }

            public void setChannelCode(String channelCode) {
                this.channelCode = channelCode;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public static class LiveBean {
                private LiveDurationBean liveDuration;
                private String url;

                public LiveDurationBean getLiveDuration() {
                    return liveDuration;
                }

                public void setLiveDuration(LiveDurationBean liveDuration) {
                    this.liveDuration = liveDuration;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public static class LiveDurationBean {
                    private String startTime;
                    private String endTime;

                    public String getStartTime() {
                        return startTime;
                    }

                    public void setStartTime(String startTime) {
                        this.startTime = startTime;
                    }

                    public String getEndTime() {
                        return endTime;
                    }

                    public void setEndTime(String endTime) {
                        this.endTime = endTime;
                    }
                }
            }

            public static class VideoBean {
                private String url;
                private String url2;
                private String duration;
                private String fsize;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getUrl2() {
                    return url2;
                }

                public void setUrl2(String url2) {
                    this.url2 = url2;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getFsize() {
                    return fsize;
                }

                public void setFsize(String fsize) {
                    this.fsize = fsize;
                }
            }

            public static class AudioBean {
                private String url;
                private String duration;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }
            }


            public static class ChannelBean {
                private String channelId;
                private String channelTitle;
                private String channelNav;

                public String getChannelId() {
                    return channelId;
                }

                public void setChannelId(String channelId) {
                    this.channelId = channelId;
                }

                public String getChannelTitle() {
                    return channelTitle;
                }

                public void setChannelTitle(String channelTitle) {
                    this.channelTitle = channelTitle;
                }

                public String getChannelNav() {
                    return channelNav;
                }

                public void setChannelNav(String channelNav) {
                    this.channelNav = channelNav;
                }
            }
        }
    }
    public static class ChannelsBean {
        private String channelId;
        private String channelUrl;
        private String appChannelDesc;
        private String channelCode;
        private String firstScreen;
        private String isFixed;
        private List<String> channelLogo;
        private String channelType;
        private String linkUrl;
        private String hasChildren;
        private String channelComment;

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelUrl() {
            return channelUrl;
        }

        public void setChannelUrl(String channelUrl) {
            this.channelUrl = channelUrl;
        }

        public String getAppChannelDesc() {
            return appChannelDesc;
        }

        public void setAppChannelDesc(String appChannelDesc) {
            this.appChannelDesc = appChannelDesc;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public String getFirstScreen() {
            return firstScreen;
        }

        public void setFirstScreen(String firstScreen) {
            this.firstScreen = firstScreen;
        }

        public String getIsFixed() {
            return isFixed;
        }

        public void setIsFixed(String isFixed) {
            this.isFixed = isFixed;
        }

        public List<String> getChannelLogo() {
            return channelLogo;
        }

        public void setChannelLogo(List<String> channelLogo) {
            this.channelLogo = channelLogo;
        }

        public String getChannelType() {
            return channelType;
        }

        public void setChannelType(String channelType) {
            this.channelType = channelType;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getHasChildren() {
            return hasChildren;
        }

        public void setHasChildren(String hasChildren) {
            this.hasChildren = hasChildren;
        }

        public String getChannelComment() {
            return channelComment;
        }

        public void setChannelComment(String channelComment) {
            this.channelComment = channelComment;
        }
    }
}
